package org.eclipselabs.couchemf.json.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipselabs.couchemf.json.JSONCouchDBHandler;
import org.eclipselabs.emfjson.json.JSONPackage;
import org.eclipselabs.emfjson.json.resource.JSONResourceFactory;
import org.junit.Before;
import org.junit.Test;

public class JSONCouchDBHandlerTest {

	ResourceSet resourceSet;

	@Before
	public void setUp() {
		resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new JSONResourceFactory());
		resourceSet.getURIConverter().getURIHandlers().add(0, new JSONCouchDBHandler());
	}

	@Test
	public void testListOfDatabases() throws IOException {
		Resource resource = resourceSet.createResource(URI.createURI("http://127.0.0.1:5984/_all_dbs"));
		assertNotNull(resource);

		resource.load(null);

		assertEquals(1, resource.getContents().size());
		assertEquals(JSONPackage.Literals.JARRAY, resource.getContents().get(0).eClass());		
	}

	@Test
	public void testListOfDocuments() throws IOException {
		Resource resource = resourceSet.createResource(URI.createURI("http://127.0.0.1:5984/sample/_all_docs"));
		assertNotNull(resource);

		resource.load(null);

		assertEquals(1, resource.getContents().size());
		assertEquals(JSONPackage.Literals.JOBJECT, resource.getContents().get(0).eClass());
	}

}
