package org.emfjson.couchemf.streams;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.URIConverter.Saveable;
import org.emfjson.common.Options;
import org.emfjson.couchemf.client.CouchClient;
import org.emfjson.jackson.map.ObjectWriter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class CouchOutputStream extends ByteArrayOutputStream implements Saveable {

	private URI uri;
	private Map<?, ?> options;

	public CouchOutputStream(URI uri, Map<?, ?> options) {
		this.uri = uri;
		this.options = options;
	}

	@Override
	public void saveResource(Resource resource) throws IOException {
		final CouchClient client = new CouchClient.Builder().url(uri.toString()).build();

		JsonNode node = toJson(resource);
		client.put(node);
	}

	private JsonNode toJson(Resource resource) {
		final ObjectMapper jmapper = new ObjectMapper();
		final ObjectWriter writer = new ObjectWriter(jmapper, resource, Options.from(options).build());

		final JsonNode contents = writer.toNode();
		final ObjectNode resourceNode = jmapper.createObjectNode();
		final String id = uri.segment(2);

		resourceNode.put("_id", id);
		resourceNode.set("contents", contents);

		return resourceNode;
	}

}
