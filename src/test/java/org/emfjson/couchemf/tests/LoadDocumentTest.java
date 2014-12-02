package org.emfjson.couchemf.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.eclipse.emf.ecore.resource.Resource;
import org.emfjson.couchemf.tests.model.ANode;
import org.emfjson.couchemf.tests.model.BNode;
import org.emfjson.couchemf.tests.model.ModelFactory;
import org.emfjson.couchemf.tests.model.ModelPackage;
import org.emfjson.couchemf.tests.model.Node;
import org.junit.Before;
import org.junit.Test;

public class LoadDocumentTest extends CouchTestSupport {

	private Node createModel() {
		Node root = ModelFactory.eINSTANCE.createNode();
		root.setLabel("Root");
		root.setValue("myValue");
		
		ANode a = ModelFactory.eINSTANCE.createANode();
		a.setLabel("a");
		BNode b = ModelFactory.eINSTANCE.createBNode();
		b.setLabel("b");
		a.setBNode(b);
		
		root.getNodes().add(a);
		root.getNodes().add(b);

		return root;
	}

	@Before
	@Override
	public void setUp() {
		super.setUp();
		
		Resource res = resourceSet.createResource(baseURI.appendSegment("nodes"));
		res.getContents().add(createModel());

		try {
			res.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}

		res.unload();
	}
	
	@Test
	public void testLoadDocumentByID() throws IOException {
		Resource resource = resourceSet.createResource(baseURI.appendSegment("nodes"));
		assertNotNull(resource);

		resource.load(null);
		assertEquals(1, resource.getContents().size());
		
		assertEquals(ModelPackage.Literals.NODE, resource.getContents().get(0).eClass());
		
		Node n = (Node) resource.getContents().get(0);
		assertEquals("Root", n.getLabel());
		assertEquals("myValue", n.getValue());
		assertEquals(2, n.getNodes().size());

		assertEquals(ModelPackage.Literals.ANODE, n.getNodes().get(0).eClass());
		assertEquals(ModelPackage.Literals.BNODE, n.getNodes().get(1).eClass());

		ANode a = (ANode) n.getNodes().get(0);
		BNode b = (BNode) n.getNodes().get(1);

		assertEquals(b, a.getBNode());
	}

}
