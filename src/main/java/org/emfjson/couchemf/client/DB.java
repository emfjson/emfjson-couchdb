package org.emfjson.couchemf.client;

import java.io.IOException;

import javax.xml.ws.http.HTTPException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

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
	 * @throws HTTPException
	 */
	public JsonNode docs(String dbName) throws IOException {
		if (dbName == null) return null;

		return client.json(
			client
			.http
			.send("GET")
			.to(dbName + "/" + Constants.allDocs)
			.execute()
		);
	}

	/**
	 * Returns true if the CouchDB instance has this database.
	 * 
	 * @return {@link Boolean}
	 */
	public boolean exist() {
		JsonNode node = null;
		try {
			node = client.json(client.http.send("GET").to(dbName).execute());
		} catch (HTTPException e) {
			return false;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return node.has("db_name");
	}
	
	/**
	 * Creates this database in the CouchDB instance.
	 * 
	 * @return JsonNode
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public JsonNode create() throws JsonProcessingException, IOException {
		return client.json(
			client
			.http
			.send("PUT")
			.to(dbName)
			.execute()
		);
	}

	/**
	 * Deletes this database in the CouchDB instance.
	 * 
	 * @return JsonNode
	 * @throws HTTPException
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public JsonNode delete() throws JsonProcessingException, IOException {
		return client.json(
			client
			.http
			.send("DELETE")
			.to(dbName)
			.execute()
		);
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

	public JsonNode info() throws JsonProcessingException, IOException {
		return client.json(
			client
			.http
			.send("GET")
			.to(dbName)
			.execute()
		);
	}

}
