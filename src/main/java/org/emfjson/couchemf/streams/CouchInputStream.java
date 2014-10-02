package org.emfjson.couchemf.streams;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.URIConverter.Loadable;
import org.emfjson.common.Options;
import org.emfjson.couchemf.client.CouchClient;
import org.emfjson.jackson.map.ObjectReader;

import com.fasterxml.jackson.databind.JsonNode;

public class CouchInputStream extends InputStream implements Loadable {

	private final URI uri;
	private final Map<?, ?> options;

	public CouchInputStream(URI uri, Map<?, ?> options) {
		this.uri = uri;
		this.options = options;
	}

	@Override
	public void loadResource(Resource resource) throws IOException {
		final CouchClient client = new CouchClient.Builder().url(uri.toString()).build();

		if (!resource.getContents().isEmpty()) {
			resource.getContents().clear();
		}

		readJson(resource, client.get());
	}

	private void readJson(Resource resource, JsonNode data) throws IOException {
		System.out.println("loaded " + data);
		final JsonNode contents = data.get("contents");
		final ObjectReader reader = new ObjectReader(resource, Options.from(options).build());
		final EObject result = reader.from(contents);

		if (result != null) {
			resource.getContents().add(result);
			reader.resolveEntries();
		}
	}

	@Override
	public int read() throws IOException {
		return 0;
	}
	
}
