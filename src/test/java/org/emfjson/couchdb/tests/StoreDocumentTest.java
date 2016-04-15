package org.emfjson.couchdb.tests;

import com.fasterxml.jackson.databind.JsonNode;
import org.eclipse.emf.ecore.resource.Resource;
import org.emfjson.couchdb.client.CouchClient;
import org.emfjson.couchdb.client.DB;
import org.emfjson.model.ModelFactory;
import org.emfjson.model.TestA;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StoreDocumentTest extends CouchTestSupport {

	private CouchClient client = new CouchClient();

	@Before
	@Override
	public void setUp() throws IOException {
		super.setUp();
	}

	@After
	public void tearDown() throws IOException {
		client.db("models").delete();
	}

	@Test
	public void testStoreDocumentWithOneObject() throws IOException {
		// create and save resource
		Resource resource = resourceSet.createResource(baseURI.appendSegment("test1"));

		TestA node = ModelFactory.eINSTANCE.createTestA();
		node.setStringValue("test1");
		node.setBooleanValue(true);

		resource.getContents().add(node);
		resource.save(null);

		// check added document
		DB db = client.db("models");
		JsonNode result = db.doc("test1").content();

		assertTrue(result.has("_id"));
		assertTrue(result.has("contents"));

		JsonNode contents = result.get("contents");

		assertTrue(contents.has("eClass"));
		assertEquals("http://eclipselabs.org/couchemf/junit/model#//ANode", contents.get("eClass").asText());
		assertTrue(contents.has("label"));
		assertEquals("test1", contents.get("label").asText());
		assertTrue(contents.has("value"));
		assertEquals("test1", contents.get("value").asText());

		// revision is added to resource URI
		assertTrue(resource.getURI().hasQuery());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testStoreDocumentWithTwoRootObjects() throws IOException {
		Resource resource = resourceSet.createResource(baseURI.appendSegment("test1"));

		TestA a1 = ModelFactory.eINSTANCE.createTestA();
		a1.setStringValue("a1");
		TestA a2 = ModelFactory.eINSTANCE.createTestA();
		a2.setStringValue("a2");

		resource.getContents().add(a1);
		resource.getContents().add(a2);

		resource.save(null);
	}

	@Test
	public void testStoreDocumentWithOneObjectAndUpdate() throws IOException {
		Resource resource = resourceSet.createResource(baseURI.appendSegment("test1"));

		TestA a1 = ModelFactory.eINSTANCE.createTestA();
		a1.setStringValue("a1");

		resource.getContents().add(a1);
		resource.save(null);

		a1.setStringValue("a2");
		resource.save(null);
	}

//	@Test
//	public void testStoreHierarchyOfObjects() throws IOException {
//		Node n = ModelFactory.eINSTANCE.createNode();
//		n.setLabel("root");
//
//		Node n1 = ModelFactory.eINSTANCE.createNode();
//		n1.setLabel("n1");
//		Node n12 = ModelFactory.eINSTANCE.createNode();
//		n12.setLabel("n12");
//		Node n123 = ModelFactory.eINSTANCE.createNode();
//		n123.setLabel("n123");
//		Node n2 = ModelFactory.eINSTANCE.createNode();
//		n2.setLabel("n2");
//		Node n21 = ModelFactory.eINSTANCE.createNode();
//		n21.setLabel("n21");
//
//		n.getChild().add(n1);
//		n.getChild().add(n2);
//		n1.getChild().add(n12);
//		n12.getChild().add(n123);
//		n2.getChild().add(n21);
//
//		n.setTarget(n2);
//		n123.getManyRef().add(n21);
//		n123.getManyRef().add(n123);
//
//		Resource resource = resourceSet.createResource(baseURI.appendSegment("nodes"));
//		resource.getContents().add(n);
//
//		resource.save(null);
//	}
//
//	@Test
//	public void testCreateStoreDocumentNotExistent() throws IOException {
//		Resource resource = resourceSet.createResource(baseURI.appendSegment("emfjson_test").appendSegment("test_create"));
//
//		User u1 = ModelFactory.eINSTANCE.createUser();
//		u1.setUserId("1");
//		u1.setName("John");
//
//		resource.getContents().add(u1);
//
//		resource.save(null);
//	}
//
//	@Test
//	public void testCreateStoreDocumentDatabaseNotExistent() throws IOException {
//		Resource resource = resourceSet.createResource(baseURI.appendSegment("emfjson_not_exist").appendSegment("test_create"));
//
//		User u1 = ModelFactory.eINSTANCE.createUser();
//		u1.setUserId("1");
//		u1.setName("John");
//
//		resource.getContents().add(u1);
//
//		resource.save(null);
//	}

}
