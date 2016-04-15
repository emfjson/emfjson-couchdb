package org.emfjson.couchdb.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import javax.xml.ws.http.HTTPException;
import java.io.IOException;

public class DB {

	private final CouchClient client;
	private final String dbName;

	DB(CouchClient client, String dbName) {
		this.dbName = dbName;
		this.client = client;
	}

	/**
	 * Returns a built-in view of all documents in this database.
	 *
	 * @param dbName
	 * @return {@link JsonNode}
	 * @throws IOException
	 */
	public JsonNode docs(String dbName) throws IOException {
		if (dbName == null) return null;

		return client.content(dbName + "/" + Constants._all_docs);
	}

	/**
	 * Returns true if the CouchDB instance has this database.
	 *
	 * @return {@link Boolean}
	 */
	public boolean exist() {
		JsonNode node = null;
		try {
			node = client.content(dbName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return node != null && node.has("db_name");
	}

	/**
	 * Creates this database in the CouchDB instance.
	 *
	 * @return JsonNode
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public JsonNode create() throws IOException {
		return client.put(dbName, "");
	}

	/**
	 * Deletes this database in the CouchDB instance.
	 *
	 * @return JsonNode
	 * @throws HTTPException
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public JsonNode delete() throws IOException {
		return client.delete(dbName);
	}

	/**
	 * Returns the document
	 *
	 * @param docName
	 * @return {@link CouchDocument}
	 * @throws IOException
	 * @throws HTTPException
	 */
	public CouchDocument doc(String docName) throws IOException {
		if (dbName == null || docName == null) return null;

		return new CouchDocument(this.client, this, docName);
	}

	public String getName() {
		return dbName;
	}

	public JsonNode info() throws IOException {
		return client.content(dbName);
	}

}
