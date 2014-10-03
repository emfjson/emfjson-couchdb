package org.emfjson.couchemf.streams;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.URIConverter.Saveable;
import org.emfjson.common.Options;
import org.emfjson.couchemf.client.CouchClient;
import org.emfjson.couchemf.client.CouchDocument;
import org.emfjson.couchemf.client.DB;
import org.emfjson.jackson.map.ObjectWriter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

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

//		if (!doc.exist()) {
		JsonNode status = doc.create(toJson(resource));
//		} else {
//		}

		if (status != null && status.has("ok")) {
			if (status.get("ok").asBoolean()) {
				String rev = status.get("rev").asText();
				URI newURI = resource.getURI().appendQuery("rev=" + rev);
				resource.setURI(newURI);
			}
		}
	}

	private JsonNode toJson(Resource resource) {
		final ObjectMapper jmapper = new ObjectMapper();
		final ObjectWriter writer = new ObjectWriter(jmapper, resource, Options.from(options).build());

		final JsonNode contents = writer.toNode();
		final ObjectNode resourceNode = jmapper.createObjectNode();
		final String id = uri.segment(1);

		resourceNode.put("_id", id);
		resourceNode.set("contents", contents);

		return resourceNode;
	}

}
