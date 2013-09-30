package org.eclipselabs.couchemf.json;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.codehaus.jackson.JsonProcessingException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.URIHandlerImpl;
import org.eclipselabs.couchemf.client.CouchClient;
import org.eclipselabs.emfjson.json.map.JSONMapper;
import org.eclipselabs.emfjson.json.streams.JSONInputStream;
import org.eclipselabs.emfjson.json.streams.JSONOutputStream;

public class JSONCouchDBHandler extends URIHandlerImpl {

	@Override
	public boolean canHandle(URI uri) {
		System.out.println(uri);
		final CouchClient client = new CouchClient.
				Builder().
				url(uri.trimQuery().toString()).
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
		final JSONMapper mapper = new JSONMapper();
		final CouchClient client = new CouchClient.
				Builder().
				url(uri.toString()).
				build();

		return new JSONInputStream(uri, options) {
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

		return new JSONOutputStream(options) {
			@Override
			public void close() throws IOException {				
				client.put(this.currentRoot);
			}
		};
	}

}
