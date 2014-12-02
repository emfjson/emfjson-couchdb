package org.emfjson.couchemf.client;

import java.io.IOException;

import javax.xml.ws.http.HTTPException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

public class CouchDocument {

	private final CouchClient client;
	private final DB db;
	private final String docName;

	public CouchDocument(CouchClient client, DB db, String docName) {
		this.client = client;
		this.db = db;
		this.docName = docName;
	}

	/**
	 * Returns true if the document is present in this CouchDB instance.
	 * 
	 */
	public boolean exist() {
		JsonNode node = client.content(db.getName() + "/" + docName);

		return node != null && node.has("_id");
	}

	/**
	 * Returns the content of the latest revision of the document
	 * 
	 * @param dbName
	 * @param docName
	 * @return JsonNode
	 * @throws IOException
	 * @throws HTTPException
	 */
	public JsonNode content() throws JsonProcessingException, IOException {
		return client.content(db.getName() + "/" + docName);
	}

	/**
	 * Creates a document from a JsonNode object in the CouchDB instance.
	 * 
	 * @param dbName
	 * @param docName
	 * @param data
	 * @return JsonNode
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public JsonNode create(JsonNode data) throws JsonProcessingException, IOException {
		return create(client.mapper.writeValueAsString(data));
	}

	/**
	 * Creates a document from a String in the CouchDB instance.
	 * 
	 * @param dbName
	 * @param docName
	 * @param data
	 * @return JsonNode
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public JsonNode create(String data) throws JsonProcessingException, IOException {
		return client.put(db.getName() + "/" + docName, data);
	}

	/**
	 * Deletes this document from this database in the CouchDB instance.
	 * 
	 * @param dbName
	 * @param docName
	 * @return JsonNode
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public JsonNode delete() throws JsonProcessingException, IOException {
		return client.delete(db.getName() + "/" + docName);
	}
	
	/**
	 * Deletes this document with this revision in the CouchDB instance.
	 * 
	 * @param dbName
	 * @param docName
	 * @param revision
	 * @return JsonNode
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public JsonNode delete(String revision) throws JsonProcessingException, IOException {
		if (revision.contains("=")) {
			revision = revision.split("=")[1];
		}

		return client.delete(db.getName() + "/" + docName + "?rev=" + revision);
	}

	public String getName() {
		return docName;
	}

}
