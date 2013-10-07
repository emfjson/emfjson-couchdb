package org.eclipselabs.couchemf.emfjson.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipselabs.couchemf.client.CouchClient;
import org.eclipselabs.couchemf.emfjson.CouchDBHandler;
import org.eclipselabs.couchemf.emfjson.junit.model.ANode;
import org.eclipselabs.couchemf.emfjson.junit.model.ModelFactory;
import org.eclipselabs.emfjson.resource.JsResourceFactoryImpl;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

public class TestStoreDocument {
	
	ResourceSet resourceSet;
	URI baseURI = URI.createURI("http://eclipselabs.org/models");
	URI couchURI = URI.createURI("http://127.0.0.1:5984/models");

	@Before
	public void setUp() {
		resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new JsResourceFactoryImpl());
		resourceSet.getURIConverter().getURIHandlers().add(0, new CouchDBHandler());
		
		resourceSet.getURIConverter().getURIMap().put(
				baseURI.appendSegment(""), 
				couchURI.appendSegment(""));		
	}

	@Test
	public void testStoreDocumentWithOneObject() throws IOException {
		CouchClient client = new CouchClient.Builder().build();
		// check state of couchdb		
		assertTrue(client.isConnected());
		assertTrue(client.hasDatabase("models"));
		JsonNode result = client.doc("models", "test1");		
		assertTrue(result.has("error"));
		assertEquals("not_found", result.get("error").asText());
		
		// create and save resource
		Resource resource = resourceSet.createResource(baseURI.appendSegment("test1"));

		ANode node = ModelFactory.eINSTANCE.createANode();
		node.setLabel("test1");
		node.setValue("test1");

		resource.getContents().add(node);
		resource.save(null);
		
		// check added document
		result = client.doc("models", "test1");

		assertTrue(result.has("_id"));
		assertTrue(result.has("eClass"));
		assertEquals("http://eclipselabs.org/couchemf/junit/model#//ANode", result.get("eClass").asText());
		assertTrue(result.has("label"));
		assertEquals("test1", result.get("label").asText());
		assertTrue(result.has("value"));
		assertEquals("test1", result.get("value").asText());

		// revision is added to resource URI
		assertTrue(resource.getURI().hasQuery());

		// delete document
		result = client.deleteDocument("models", "test1", resource.getURI().query());
		result = client.doc("models", "test1");

		assertTrue(result.has("error"));
		assertEquals("not_found", result.get("error").asText());
	}
/**	
	@Test(expected=IllegalArgumentException.class)
	public void testStoreDocumentWithTwoRootObjects() throws IOException {
		Resource resource = resourceSet.createResource(URI.createURI(url));
		
		User u1 = ModelFactory.eINSTANCE.createUser();
		u1.setUserId("1");
		u1.setName("John");
		User u2 = ModelFactory.eINSTANCE.createUser();
		u2.setUserId("2");
		u2.setName("Paul");
		u1.getFriends().add(u2);
		
		resource.getContents().add(u1);
		resource.getContents().add(u2);
		
		resource.save(null);
	}
	
	@Test
	public void testStoreDocumentWithOneObjectAndUpdate() throws IOException {
		Resource resource = resourceSet.createResource(URI.createURI(url));
		
		User u1 = ModelFactory.eINSTANCE.createUser();
		u1.setUserId("1");
		u1.setName("John");
		
		resource.getContents().add(u1);
		resource.save(null);
		
		u1.setName("John Smith");
		resource.save(null);
	}
	
	@Test
	public void testStoreHierarchyOfObjects() throws IOException {
		Node n = ModelFactory.eINSTANCE.createNode();
		n.setLabel("root");
		
		Node n1 = ModelFactory.eINSTANCE.createNode();
		n1.setLabel("n1");
		Node n12 = ModelFactory.eINSTANCE.createNode();
		n12.setLabel("n12");
		Node n123 = ModelFactory.eINSTANCE.createNode();
		n123.setLabel("n123");
		Node n2 = ModelFactory.eINSTANCE.createNode();
		n2.setLabel("n2");
		Node n21 = ModelFactory.eINSTANCE.createNode();
		n21.setLabel("n21");
		
		n.getChild().add(n1);
		n.getChild().add(n2);
		n1.getChild().add(n12);
		n12.getChild().add(n123);
		n2.getChild().add(n21);
		
		n.setTarget(n2);
		n123.getManyRef().add(n21);
		n123.getManyRef().add(n123);
		
		Resource resource = resourceSet.createResource(baseURI.appendSegment("nodes"));
		resource.getContents().add(n);
		
		resource.save(null);
	}
	
	@Test
	public void testCreateStoreDocumentNotExistant() throws IOException {
		Resource resource = resourceSet.createResource(baseURI.appendSegment("emfjson_test").appendSegment("test_create"));
		
		User u1 = ModelFactory.eINSTANCE.createUser();
		u1.setUserId("1");
		u1.setName("John");
		
		resource.getContents().add(u1);
		
		resource.save(null);
	}
	
	@Test
	public void testCreateStoreDocumentDatabaseNotExistant() throws IOException {
		Resource resource = resourceSet.createResource(baseURI.appendSegment("emfjson_not_exist").appendSegment("test_create"));
		
		User u1 = ModelFactory.eINSTANCE.createUser();
		u1.setUserId("1");
		u1.setName("John");
		
		resource.getContents().add(u1);
		
		resource.save(null);
	}
**/
}
