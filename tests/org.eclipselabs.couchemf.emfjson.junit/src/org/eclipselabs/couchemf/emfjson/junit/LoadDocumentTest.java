package org.eclipselabs.couchemf.emfjson.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipselabs.couchemf.emfjson.CouchDBHandler;
import org.eclipselabs.emfjson.resource.JsResourceFactoryImpl;
import org.junit.Before;
import org.junit.Test;

public class LoadDocumentTest {

	private ResourceSet resourceSet;
	private URI baseURI = URI.createURI("http://eclipselabs.org/models");
	private URI couchURI = URI.createURI("http://127.0.0.1:5984/models");

	@Before
	public void setUp() {
		resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new JsResourceFactoryImpl());
		resourceSet.getURIConverter().getURIHandlers().add(0, new CouchDBHandler());
		
		resourceSet.getURIConverter().getURIMap().put(baseURI.appendSegment(""), couchURI.appendSegment(""));
	}

	@Test
	public void testLoadDocumentByID() throws IOException {
		Resource resource = resourceSet.createResource(baseURI.appendSegment("nodes"));
		assertNotNull(resource);
		System.out.println(resource.getURI());
		resource.load(null);
		assertEquals(0, resource.getContents().size());	
	}

}
