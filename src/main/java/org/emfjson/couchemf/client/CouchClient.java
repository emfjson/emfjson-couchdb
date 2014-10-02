package org.emfjson.couchemf.client;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Observable;

import javax.xml.ws.http.HTTPException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Simple client for CouchDB.
 * 
 * See {@link CouchClient.Builder} for creation.
 *
 */
public class CouchClient extends Observable {

	private final HttpClient client;
	private final ObjectMapper mapper = new ObjectMapper();	

	private static final String allDbs = "_all_dbs";
	private static final String allDocs = "_all_docs";

	private final URL baseURL;
	private final String path;

	private CouchClient(URL baseURL, String path) {
		this.baseURL = baseURL;
		this.path = path;
		this.client = new HttpClient(baseURL);
	}

	/**
	 * Returns the CouchDB instance URL.
	 * 
	 * @return URL
	 */
	public URL url() {
		return baseURL;
	}

	/**
	 * Returns true if the connection to a CouchDB instance is established.
	 * 
	 * @return {@link Boolean}
	 * @throws IOException
	 * @throws JsonProcessingException
	 */
	public boolean isConnected() throws IOException, JsonProcessingException {
		String result = client.send("GET").execute();
		System.out.println("result " + result);
		return mapper.readTree(result).has("couchdb");
	}

	/**
	 * Returns list of all of the databases in a CouchDB instance.
	 * 
	 * @return {@link JsonNode}
	 * @throws IOException
	 * @throws JsonProcessingException
	 * @throws HTTPException
	 */
	public JsonNode dbs() throws IOException, JsonProcessingException {
		return json(client.send("GET").to(allDbs).execute());
	}

	/**
	 * Returns database information.
	 * 
	 * @param dbName
	 * @return {@link JsonNode}
	 * @throws IOException
	 * @throws JsonProcessingException
	 * @throws HTTPException
	 */
	public JsonNode db(String dbName) throws IOException, JsonProcessingException {
		if (dbName == null) return null;

		return json(client.send("GET").to(dbName).execute());
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

		return json(client.send("GET").to(dbName + "/" + allDocs).execute());
	}

	/**
	 * Returns the latest revision of the document
	 * 
	 * @param dbName
	 * @param docName
	 * @return {@link JsonNode}
	 * @throws IOException
	 * @throws HTTPException
	 */
	public JsonNode doc(String dbName, String docName) throws IOException {
		if (dbName == null || docName == null) return null;

		return json(client.send("GET").to(dbName + "/" + docName).execute());
	}

	/**
	 * Returns value of the current client.
	 * 
	 * @return {@link JsonNode}
	 * @throws JsonProcessingException
	 * @throws IOException
	 * @throws HTTPException
	 */
	public JsonNode get() throws JsonProcessingException, IOException {
		return json(client.send("GET").to(path).execute());
	}
	
	/**
	 * Returns true if the CouchDB instance has this database.
	 * 
	 * @param dbName
	 * @return {@link Boolean}
	 */
	public boolean hasDatabase(String dbName) {
		JsonNode node = null;
		try {
			node = json(client.send("GET").to(dbName).execute());
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
	 * @param dbName
	 * @return {@link JsonNode}
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public JsonNode createDatabase(String dbName) throws JsonProcessingException, IOException {
		return json(client.send("PUT").to(dbName).execute());
	}

	/**
	 * Deletes this database in the CouchDB instance.
	 * 
	 * @param dbName
	 * @return {@link JsonNode}
	 * @throws HTTPException
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public JsonNode deleteDatabase(String dbName) throws JsonProcessingException, IOException {
		return json(client.send("DELETE").to(dbName).execute());
	}

	/**
	 * Creates a document from a JsonNode object in the CouchDB instance.
	 * 
	 * @param dbName
	 * @param docName
	 * @param data
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public JsonNode createDocument(String dbName, String docName, JsonNode data) throws JsonProcessingException, IOException {
		return createDocument(dbName, docName, mapper.writeValueAsString(data));
	}

	/**
	 * Creates a document from a String in the CouchDB instance.
	 * 
	 * @param dbName
	 * @param docName
	 * @param data
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public JsonNode createDocument(String dbName, String docName, String data) throws JsonProcessingException, IOException {
		return json(client.send("PUT", data).to(dbName + "/" + docName).execute());
	}

	/**
	 * Deletes this document from this database in the CouchDB instance.
	 * 
	 * @param dbName
	 * @param docName
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public JsonNode deleteDocument(String dbName, String docName) throws JsonProcessingException, IOException {
		return json(client.send("DELETE").to(dbName + "/" + docName).execute());
	}
	
	/**
	 * Deletes this document with this revision in the CouchDB instance.
	 * 
	 * @param dbName
	 * @param docName
	 * @param revision
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public JsonNode deleteDocument(String dbName, String docName, String revision) throws JsonProcessingException, IOException {
		if (revision.contains("="))
			revision = revision.split("=")[1];

		return json(client.send("DELETE").to(dbName + "/" + docName).q("rev=" + revision).execute());
	}

	public JsonNode put(JsonNode node) throws IOException {
		String data = null;
		String result = null;
		try {
			data = mapper.writeValueAsString(node);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		

		if (data != null) {
			result = client.send("PUT", data).to(path).execute();
		}

		if (result != null) {
			return json(result);
		} else {
			return null;
		}
	}

	private JsonNode json(String value) throws JsonProcessingException, IOException {
		return value == null ? null : mapper.readTree(value);
	}

	@Override
	public synchronized boolean hasChanged() {
		return super.hasChanged();
	}

	/**
	 * {@link CouchClient} builder class.
	 * 
	 * <pre>
	 * 
	 * // Using default address (http://127.0.0.1:5984)
	 * //
	 * CouchClient client = new CouchClient.Builder().build();
	 * 
	 * // Specific CouchDB address
	 * // 
	 * CouchClient client = new CouchClient.Builder()
	 * 	.url(http://some.couch.address:3455/)
	 * 	.build();
	 * </pre>
	 *
	 */
	public static class Builder {

		private final String defaultUrl = "http://127.0.0.1:5984/";
		private String url = null;
		public Builder() {}

		public Builder url(String url) {
			this.url = url;
			return this;
		}

		public CouchClient build() {
			URL clientUrl = null;
			if (this.url == null) {
				try {
					clientUrl = new URL(this.defaultUrl);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			} else {
				try {
					clientUrl = new URL(this.url);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}

			String path = null;
			if (!clientUrl.getPath().isEmpty()) {
				path = clientUrl.getPath();
			}

			try {
				clientUrl = HttpClient.baseURL(clientUrl);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			
			return new CouchClient(clientUrl, path);
		}

	}

}
