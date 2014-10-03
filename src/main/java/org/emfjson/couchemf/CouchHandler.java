package org.emfjson.couchemf;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.URIHandlerImpl;
import org.emfjson.couchemf.client.CouchClient;
import org.emfjson.couchemf.streams.CouchInputStream;
import org.emfjson.couchemf.streams.CouchOutputStream;

import com.fasterxml.jackson.core.JsonProcessingException;

public class CouchHandler extends URIHandlerImpl {

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
		final CouchClient client = getClient(uri);

		return new CouchInputStream(client, uri, options);
	}

	@Override
	public OutputStream createOutputStream(URI uri, Map<?, ?> options) throws IOException {
		final CouchClient client = getClient(uri);

		return new CouchOutputStream(client, uri, options);
	}

	private CouchClient getClient(URI uri) throws IOException {
		URI baseURI = uri.trimQuery().trimFragment().trimSegments(uri.segmentCount());
		URL url;
		try {
			url = new URL(baseURI.toString());
		} catch (MalformedURLException e) {
			throw e;
		}

		return new CouchClient(url);
	}

}
