# CouchDB for EMF

## Downloads

 - Eclipse Update Site: http://ghillairet.github.com/p2

## Dependencies

  - Eclipse [EMF](http://www.eclipse.org/emf) 2.6.0
  - [EMFJSON](http://github.com/ghillairet/emfjson) 0.5.0

## License

  - [Eclipse Public License](http://www.eclipse.org/legal/epl-v10.html)

## Starter guide

EMFJs offers support for CouchDB through the implementation of a custom [URIHandler](http://download.eclipse.org/modeling/emf/emf/javadoc/2.7.0/org/eclipse/emf/ecore/resource/impl/URIHandlerImpl.html). This page will show you how store, update and retrieve EMF Resources from CouchDB.

### Install CouchDB

First of all [download](http://couchdb.apache.org/downloads.html) and install CouchDB.

Then start couchdb in a terminal:
``$ couchdb``

Then go to http://127.0.0.1:5984/_utils/index.html, you can now access couchdb web client.

### Storing Resources as Documents
_This part suppose that you are familiar with the concepts of EMF Resource, ResourceSet and URI._

The access of EMF resources from CouchDB is done via the Resource API of EMF. To enable the CouchDB support for your EMF models, simply add the following line after you create a ResourceSet.

```java
ResourceSet resourceSet = new ResourceSetImpl();
resourceSet.getURIConverter().getURIHandlers().add(0, new CouchDBHandler());
```

When you create a Resource, use the URI of the CouchDB server (e.g. http://127.0.0.1:5984/), as shown below. The first segment of the URI (here users) is the name of the database that will be used to store the JSON document corresponding to your EMF model.

```java
Resource resource = resourceSet.createResource(URI.createURI("http://127.0.0.1:5984/users"));
```

You can now create an object and add it to your Resource.

```java
User user = ModelFactory.eINSTANCE.createUser();
user.setUserId("1");
user.setName("John");
resource.getContents().add(user);
```

You can now save the Resource. The content will be translated into JSOn and save as a CouchDB document.
```java
resource.save(null);
```

Resulting document:
```javascript
{
   "_id": "fc4dc3af46a4d5a2a3798819260008ea",
   "_rev": "1-3a14223e845eb306b1b43763b967b141",
   "eClass": "http://www.eclipselabs.org/emfjson/junit#//User",
   "userId": "1",
   "name": "John"
}
```

Note that when you set only one fragment to the Resource URI, it will create a new document in the corresponding database. After calling save, the Resource URI is updated with the ID of the newly created document.
For example:

```java
resource.getURI() -> http://127.0.0.1:5984/users/ee16fdb915274370e9976fc1fd00ad6f
```

![CouchDB](http://dl.dropbox.com/u/43033733/Screen%20shot%202012-01-30%20at%2012.21.50.png)

### Updating a Document

The particularity of CouchDB is that it provides a built-in mechanism to handle revisions. If you make changes to the objects contained in a Resource, and re-save it, it will create a new revision of the CouchDB document.

Modify a property of an object:
```java
user.setName("John Smith");
```

Save the modified resource:
```java
resource.save(null);
```

You should now notice the presence of different revisions for the corresponding document in the CouchDB Web interface.
![CouchDB](http://dl.dropbox.com/u/43033733/Screen%20shot%202012-01-30%20at%2012.23.46.png)
