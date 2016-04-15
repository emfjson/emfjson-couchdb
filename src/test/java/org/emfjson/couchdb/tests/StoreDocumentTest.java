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
		assertEquals("http://emfjson.org/model#//TestA", contents.get("eClass").asText());

		assertTrue(contents.has("stringValue"));
		assertEquals("test1", contents.get("stringValue").asText());

		assertTrue(contents.has("booleanValue"));
		assertEquals(true, contents.get("booleanValue").asBoolean());

		// revision is added to resource URI
		assertTrue(resource.getURI().hasQuery());
	}

	@Test
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

}
