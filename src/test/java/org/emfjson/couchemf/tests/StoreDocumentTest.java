package org.emfjson.couchemf.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.eclipse.emf.ecore.resource.Resource;
import org.emfjson.couchemf.client.CouchClient;
import org.emfjson.couchemf.client.DB;
import org.emfjson.couchemf.tests.model.ANode;
import org.emfjson.couchemf.tests.model.ModelFactory;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

public class StoreDocumentTest extends CouchTestSupport {

	private CouchClient client = new CouchClient.Builder().build();

	@Before
	@Override
	public void setUp() {
		super.setUp();
	}

	@Test
	public void testStoreDocumentWithOneObject() throws IOException {
		// create and save resource
		Resource resource = resourceSet.createResource(baseURI.appendSegment("test1"));

		ANode node = ModelFactory.eINSTANCE.createANode();
		node.setLabel("test1");
		node.setValue("test1");

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

		// delete document & db
		db.doc("models").delete();
		db.delete();
	}

//	@Test(expected=IllegalArgumentException.class)
//	public void testStoreDocumentWithTwoRootObjects() throws IOException {
//		Resource resource = resourceSet.createResource(URI.createURI(url));
//		
//		User u1 = ModelFactory.eINSTANCE.createUser();
//		u1.setUserId("1");
//		u1.setName("John");
//		User u2 = ModelFactory.eINSTANCE.createUser();
//		u2.setUserId("2");
//		u2.setName("Paul");
//		u1.getFriends().add(u2);
//		
//		resource.getContents().add(u1);
//		resource.getContents().add(u2);
//		
//		resource.save(null);
//	}
//	
//	@Test
//	public void testStoreDocumentWithOneObjectAndUpdate() throws IOException {
//		Resource resource = resourceSet.createResource(URI.createURI(url));
//		
//		User u1 = ModelFactory.eINSTANCE.createUser();
//		u1.setUserId("1");
//		u1.setName("John");
//		
//		resource.getContents().add(u1);
//		resource.save(null);
//		
//		u1.setName("John Smith");
//		resource.save(null);
//	}
//	
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
//	public void testCreateStoreDocumentNotExistant() throws IOException {
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
//	public void testCreateStoreDocumentDatabaseNotExistant() throws IOException {
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
