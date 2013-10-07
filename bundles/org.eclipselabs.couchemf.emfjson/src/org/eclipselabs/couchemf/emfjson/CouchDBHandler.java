package org.eclipselabs.couchemf.emfjson;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.URIHandlerImpl;
import org.eclipselabs.couchemf.client.CouchClient;
import org.eclipselabs.emfjson.map.EObjectMapper;
import org.eclipselabs.emfjson.streams.JsInputStream;
import org.eclipselabs.emfjson.streams.JsOutputStream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

public class CouchDBHandler extends URIHandlerImpl {

	@Override
	public boolean canHandle(URI uri) {
		final CouchClient client = new CouchClient.
				Builder().
				url(uri.toString()).
				build();

		try {			
			return client.isConnected();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public InputStream createInputStream(URI uri, Map<?, ?> options) throws IOException {
		final EObjectMapper mapper = new EObjectMapper();
		final CouchClient client = new CouchClient.
				Builder().
				url(uri.toString()).
				build();

		return new JsInputStream(uri, options) {
			@Override
			public void loadResource(Resource resource) throws IOException {
				mapper.from(client.get(), resource, options);
			}
		};
	}

	@Override
	public OutputStream createOutputStream(URI uri, Map<?, ?> options) throws IOException {
		final CouchClient client = new CouchClient.
				Builder().
				url(uri.toString()).
				build();

		return new JsOutputStream(options) {
			@Override
			public void close() throws IOException {
				JsonNode status = client.put(this.currentRoot);
				if (status != null && status.has("ok")) {
					if (status.get("ok").asBoolean()) {
						String rev = status.get("rev").asText();
						resource.setURI(resource.getURI().appendQuery("rev="+rev));
					}
				}
			}
		};
	}

}
