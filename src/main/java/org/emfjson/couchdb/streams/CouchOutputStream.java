package org.emfjson.couchdb.streams;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter.Saveable;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.emfjson.couchdb.client.CouchClient;
import org.emfjson.couchdb.client.CouchDocument;
import org.emfjson.couchdb.client.DB;
import org.emfjson.jackson.JacksonOptions;
import org.emfjson.jackson.module.EMFModule;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

public class CouchOutputStream extends ByteArrayOutputStream implements Saveable {

	private final URI uri;
	private final Map<?, ?> options;
	private final CouchClient client;

	public CouchOutputStream(CouchClient client, URI uri, Map<?, ?> options) {
		this.client = client;
		this.uri = uri;
		this.options = options;
	}

	@Override
	public void saveResource(Resource resource) throws IOException {
		final String dbName = uri.segment(0);
		final String docName = uri.segment(1);
		final DB db = client.db(dbName);

		if (!db.exist()) {
			db.create();
		}

		if (docName == null) {
			throw new IOException("Cannot load undefined document");
		}

		final CouchDocument doc = db.doc(docName);
		final JsonNode status = doc.create(toJson(resource));

		if (status != null && status.has("ok")) {
			if (status.get("ok").asBoolean()) {
				String rev = status.get("rev").asText();
				URI newURI = resource.getURI().appendQuery("rev=" + rev);
				resource.setURI(newURI);
			}
		}
	}

	private JsonNode toJson(Resource resource) {
		ResourceSet resourceSet = resource.getResourceSet();
		if (resourceSet == null) {
			resourceSet = new ResourceSetImpl();
		}

		final ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new EMFModule(resourceSet, JacksonOptions.from(options)));

		final JsonNode contents = mapper.valueToTree(resource);
		final ObjectNode resourceNode = mapper.createObjectNode();
		final String id = uri.segment(1);

		resourceNode.put("_id", id);
		resourceNode.set("contents", contents);

		return resourceNode;
	}

}
