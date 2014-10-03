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
		JsonNode node = null;
		try {
			node = client.json(
				client.http.send("GET").to(
					db.getName() + "/" + docName
				).execute()
			);
		} catch (HTTPException e) {
			return false;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return node.has("_id");
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
		return client.json(
			client
			.http
			.send("GET")
			.to(db.getName() + "/" + docName)
			.execute()
		);
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
		return client.json(
			client
			.http
			.send("PUT", data)
			.to(db.getName() + "/" + docName)
			.execute()
		);
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
		return client.json(
			client
			.http
			.send("DELETE")
			.to(db.getName() + "/" + docName)
			.execute()
		);
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

		return client.json(
			client
			.http
			.send("DELETE")
			.to(db.getName() + "/" + docName)
			.q("rev=" + revision)
			.execute()
		);
	}

	public String getName() {
		return docName;
	}

}
