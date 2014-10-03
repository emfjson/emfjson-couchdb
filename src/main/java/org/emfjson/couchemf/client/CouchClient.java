package org.emfjson.couchemf.client;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Observable;

import javax.xml.ws.http.HTTPException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Simple client for CouchDB.
 * 
 * See {@link CouchClient.Builder} for creation.
 *
 */
public class CouchClient extends Observable {

	final HttpClient http;
	final ObjectMapper mapper = new ObjectMapper();

	private final URL baseURL;

	public CouchClient(URL baseURL) {
		this.baseURL = baseURL;
		this.http = new HttpClient(baseURL);
	}

	public CouchClient() {
		this.baseURL = getDefault();	
		this.http = new HttpClient(baseURL);
	}

	private static final URL getDefault() {
		URL defaultURL = null;
		try {
			defaultURL = new URL("http://127.0.0.1:5984/");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return defaultURL;
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
		String result = http.send("GET").execute();

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
		return json(
			http
			.send("GET")
			.to(Constants.allDbs)
			.execute()
		);
	}

	/**
	 * Returns database information.
	 * 
	 * @param dbName
	 * @return {@link DB}
	 * @throws IllegalArgumentException if db name is null
	 */
	public DB db(String dbName) {
		if (dbName == null) throw new IllegalArgumentException("Db name is null");

		return new DB(this, dbName);
	}

//	/**
//	 * Returns value of the current client.
//	 * 
//	 * @return {@link JsonNode}
//	 * @throws JsonProcessingException
//	 * @throws IOException
//	 * @throws HTTPException
//	 */
//	public JsonNode get() throws JsonProcessingException, IOException {
//		return json(http.send("GET").to(path).execute());
//	}
	
	/**
	 * Returns true if the CouchDB instance has this database.
	 * 
	 * @param dbName
	 * @return {@link Boolean}
	 */
	public boolean hasDatabase(String dbName) {
		JsonNode node = null;
		try {
			node = json(http.send("GET").to(dbName).execute());
		} catch (HTTPException e) {
			return false;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return node.has("db_name");
	}

//	public JsonNode put(JsonNode node) throws IOException {
//		String data = null;
//		String result = null;
//		try {
//			data = mapper.writeValueAsString(node);
//		} catch (JsonGenerationException e) {
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}		
//
//		if (data != null) {
//			result = http.send("PUT", data).to(path).execute();
//		}
//
//		if (result != null) {
//			return json(result);
//		} else {
//			return null;
//		}
//	}

	JsonNode json(String value) throws JsonProcessingException, IOException {
		return value == null ? null : mapper.readTree(value);
	}

	@Override
	public synchronized boolean hasChanged() {
		return super.hasChanged();
	}

}
