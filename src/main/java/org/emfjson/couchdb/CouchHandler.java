package org.emfjson.couchdb;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.URIHandlerImpl;
import org.emfjson.couchdb.client.CouchClient;
import org.emfjson.couchdb.streams.CouchInputStream;
import org.emfjson.couchdb.streams.CouchOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Map;

public class CouchHandler extends URIHandlerImpl {

	@Override
	public boolean canHandle(URI uri) {
		CouchClient client;
		try {
			client = getClient(uri);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		try {
			return client.isConnected();
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
		final URI baseURI = uri.trimQuery().trimFragment().trimSegments(uri.segmentCount());
		final URL url = new URL(baseURI.toString());

		return new CouchClient(url);
	}

}
