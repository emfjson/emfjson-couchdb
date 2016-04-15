package org.emfjson.couchdb.streams;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.ContextAttributes;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter.Loadable;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.emfjson.couchdb.client.CouchClient;
import org.emfjson.couchdb.client.CouchDocument;
import org.emfjson.couchdb.client.DB;
import org.emfjson.jackson.JacksonOptions;
import org.emfjson.jackson.databind.deser.ResourceDeserializer;
import org.emfjson.jackson.module.EMFModule;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

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
			throw new IOException("Database " + dbName + " does not exist");
		}

		if (docName == null) {
			throw new IOException("Cannot load undefined document");
		}

		if (!db.doc(docName).exist()) {
			throw new IOException("Document " + docName + " does not exist");
		}

		final CouchDocument doc = db.doc(docName);

		if (!resource.getContents().isEmpty()) {
			resource.getContents().clear();
		}

		readJson(resource, doc.contentAsBytes());
	}

	private void readJson(Resource resource, byte[] data) throws IOException {
		ResourceSet resourceSet = resource.getResourceSet();
		if (resourceSet == null) {
			resourceSet = new ResourceSetImpl();
		}

		final ObjectMapper mapper = new ObjectMapper();
		final JacksonOptions jacksonOptions = JacksonOptions.from(options);

		final EMFModule module = new EMFModule(resourceSet, jacksonOptions);
		module.addDeserializer(Resource.class, new ResourceDeserializer(resourceSet, jacksonOptions) {
			@Override
			public Resource deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
				return super.deserialize(jp, ctxt);
			}
		});
		mapper.registerModule(module);

		final ContextAttributes attributes = ContextAttributes
				.getEmpty()
				.withSharedAttribute("resource", this);

		mapper.reader()
				.with(attributes)
				.forType(Resource.class)
				.readValue(data);
	}

	@Override
	public int read() throws IOException {
		return 0;
	}

}
