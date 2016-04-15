package org.emfjson.couchdb.bench;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.emfjson.EMFJs;
import org.emfjson.couchdb.CouchHandler;
import org.emfjson.jackson.resource.JsonResourceFactory;
import org.emfjson.model.ModelFactory;
import org.emfjson.model.TestA;
import org.emfjson.model.TestB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Benchmark {

	static URI couchURI = URI.createURI("http://127.0.0.1:5984/models");

	static List<EObject> createModel() {
		List<EObject> contents = new ArrayList<>();

		for (int i = 0; i < 500; i++) {

			TestA a = ModelFactory.eINSTANCE.createTestA();
			a.setStringValue("A" + i);
			contents.add(a);

			for (int j = 0; j < 200; j++) {

				TestB b = ModelFactory.eINSTANCE.createTestB();
				b.setStringValue("B" + i + "-" + j);
				a.getContainBs().add(b);
			}
		}

		return contents;
	}

	static long performSave(Resource resource, Map<String, Object> options) {
		long start = System.currentTimeMillis();
		try {
			resource.save(options);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return System.currentTimeMillis() - start;
	}

	static long times = 10;

	public static void main(String[] args) {
		long sum = 0;
		Map<String, Object> options = new HashMap<>();
		options.put(EMFJs.OPTION_INDENT_OUTPUT, false);
		options.put(EMFJs.OPTION_SERIALIZE_REF_TYPE, false);
		options.put(EMFJs.OPTION_SERIALIZE_TYPE, false);

		for (int i = 0; i < times; i++) {
			ResourceSet resourceSet = new ResourceSetImpl();
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new JsonResourceFactory());
			resourceSet.getURIConverter().getURIHandlers().add(0, new CouchHandler());

			Resource resource = resourceSet.createResource(couchURI.appendSegment("test"));
			resource.getContents().addAll(createModel());

			long cur;
			sum += cur = performSave(resource, options);
			System.out.println("JSON: " + cur / 1000.);
		}
		long average = sum / times;
		System.out.println("JSON: " + average / 1000.);
	}

}
