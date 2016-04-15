package org.emfjson.couchdb.tests;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.emfjson.couchdb.CouchHandler;
import org.emfjson.jackson.resource.JsonResourceFactory;
import org.junit.Before;

import java.io.IOException;

public class CouchTestSupport {

	protected ResourceSet resourceSet;
	protected URI baseURI = URI.createURI("http://models");
	protected URI couchURI = URI.createURI("http://127.0.0.1:5984/models");

	@Before
	public void setUp() throws IOException {
		resourceSet = new ResourceSetImpl();

		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new JsonResourceFactory());

		resourceSet.getURIConverter().getURIHandlers().add(0, new CouchHandler());

		resourceSet.getURIConverter().getURIMap().put(
				baseURI.appendSegment(""), 
				couchURI.appendSegment(""));
	}

}
