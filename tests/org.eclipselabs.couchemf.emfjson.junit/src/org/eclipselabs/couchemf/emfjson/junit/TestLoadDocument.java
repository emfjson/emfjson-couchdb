package org.eclipselabs.couchemf.emfjson.junit;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipselabs.couchemf.emfjson.CouchDBHandler;
import org.eclipselabs.emfjson.resource.JsResourceFactoryImpl;
import org.junit.Before;

public class TestLoadDocument {

	ResourceSet resourceSet;
	URI baseURI = URI.createURI("http://eclipselabs.org/models");
	URI couchURI = URI.createURI("http://127.0.0.1:5984/models");

	@Before
	public void setUp() {
		resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new JsResourceFactoryImpl());
		resourceSet.getURIConverter().getURIHandlers().add(0, new CouchDBHandler());
		
		resourceSet.getURIConverter().getURIMap().put(baseURI, couchURI);
	}
	
//	@Test
//	public void testLoadDocumentByID() throws IOException {
//		Resource resource = resourceSet.createResource(
//				baseURI.appendSegment("nodes").appendSegment("8557d948f6ef262c0af071937c0065d0"));
//		
//		assertNotNull(resource);
//		
//		resource.load(null);
//		
//		assertEquals(1, resource.getContents().size());	
//	}
	
}
