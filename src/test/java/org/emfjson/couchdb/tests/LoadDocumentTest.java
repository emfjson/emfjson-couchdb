package org.emfjson.couchdb.tests;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.emfjson.couchdb.client.CouchClient;
import org.emfjson.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class LoadDocumentTest extends CouchTestSupport {

	private CouchClient client;

	private EObject createModel() {
		TestA root = ModelFactory.eINSTANCE.createTestA();
		root.setStringValue("Root");
		root.setBooleanValue(true);
		root.setKind(TestKind.THREE);

		TestB b1 = ModelFactory.eINSTANCE.createTestB();
		b1.setStringValue("B1");

		TestB b2 = ModelFactory.eINSTANCE.createTestB();
		b2.setStringValue("B2");

		b1.setOneB(b2);

		root.getContainBs().add(b1);
		root.getContainBs().add(b2);

		return root;
	}

	@Before
	@Override
	public void setUp() throws IOException {
		super.setUp();

		client = new CouchClient();
		Resource res = resourceSet.createResource(baseURI.appendSegment("nodes"));
		res.getContents().add(createModel());

		res.save(null);
		res.unload();
	}

	@After
	public void tearDown() throws IOException {
		client.db("models").delete();
	}

	@Test
	public void testLoadDocumentByID() throws IOException {
		Resource resource = resourceSet.createResource(baseURI.appendSegment("nodes"));
		assertNotNull(resource);

		resource.load(null);
		assertEquals(1, resource.getContents().size());

		assertEquals(ModelPackage.Literals.TEST_A, resource.getContents().get(0).eClass());

		TestA n = (TestA) resource.getContents().get(0);
		assertEquals("Root", n.getStringValue());
		assertEquals(true, n.getBooleanValue());
		assertEquals(2, n.getContainBs().size());

		TestB b1 = n.getContainBs().get(0);
		TestB b2 = n.getContainBs().get(1);

		assertSame(b2, b1.getOneB());
	}

}
