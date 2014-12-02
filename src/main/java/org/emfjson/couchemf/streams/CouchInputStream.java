package org.emfjson.couchemf.streams;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.URIConverter.Loadable;
import org.emfjson.couchemf.client.CouchClient;
import org.emfjson.couchemf.client.CouchDocument;
import org.emfjson.couchemf.client.DB;
import org.emfjson.jackson.module.EMFModule;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CouchInputStream extends InputStream implements Loadable {

	private final URI uri;
	private final Map<?, ?> options;
	private final CouchClient client;

	public CouchInputStream(CouchClient client, URI uri, Map<?, ?> options) {
		this.client = client;
		this.uri = uri;
		this.options = options;
	}

	@Override
	public void loadResource(Resource resource) throws IOException {
		final String dbName = uri.segment(0);
		final String docName = uri.segment(1);
		
		final DB db = client.db(dbName);
		if (!db.exist()) {
			throw new IOException("Database "+ dbName + " does not exist");
		}

		if (docName == null) {
			throw new IOException("Cannot load undefined document");
		}

		if (!db.doc(docName).exist()) {
			throw new IOException("Document "+ docName + " does not exist");
		}

		final CouchDocument doc = db.doc(docName);

		if (!resource.getContents().isEmpty()) {
			resource.getContents().clear();
		}

		readJson(resource, doc.content());
	}

	private void readJson(Resource resource, JsonNode data) throws IOException {
		final ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new EMFModule(options));

		final EObject result = mapper.readValue(data.get("contents").traverse(), EObject.class);

		if (result != null) {
			resource.getContents().add(result);
		}
	}

	@Override
	public int read() throws IOException {
		return 0;
	}
	
}
