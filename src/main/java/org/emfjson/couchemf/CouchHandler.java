package org.emfjson.couchemf;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
		return new CouchInputStream(uri, options);
	}

	@Override
	public OutputStream createOutputStream(URI uri, Map<?, ?> options) throws IOException {
		return new CouchOutputStream(uri, options);
//		return new JsOutputStream(options) {
//			@Override
//			public void close() throws IOException {
//				JsonNode status = client.put(this.currentRoot);
//				if (status != null && status.has("ok")) {
//					if (status.get("ok").asBoolean()) {
//						String rev = status.get("rev").asText();
//						resource.setURI(resource.getURI().appendQuery("rev="+rev));
//					}
//				}
//			}
//		};
	}

}
