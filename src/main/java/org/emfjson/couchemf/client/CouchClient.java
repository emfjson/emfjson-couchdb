package org.emfjson.couchemf.client;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Observable;

import javax.xml.ws.http.HTTPException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

/**
 * Simple client for CouchDB.
 * 
 * See {@link CouchClient.Builder} for creation.
 *
 */
public class CouchClient extends Observable {

//	final HttpClient http;
	final ObjectMapper mapper = new ObjectMapper();
	
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	private OkHttpClient client = new OkHttpClient();

	private final URL baseURL;

	public CouchClient(URL baseURL) {
		this.baseURL = baseURL;
//		this.http = new HttpClient(baseURL);
	}

	public CouchClient() {
		this.baseURL = getDefault();	
//		this.http = new HttpClient(baseURL);
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
		Request request = new Request.Builder()
			.url(baseURL)
			.get()
			.build();

		Response response = client.newCall(request).execute();

		return mapper.readTree(response.body().bytes()).has("couchdb");
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
		Request request = new Request.Builder()
			.url(baseURL.toString() + Constants.allDbs)
			.get()
			.build();

		Response response = client.newCall(request).execute();
		return json(response.body().string());
//		return json(
//			http
//			.method("GET")
//			.to(Constants.allDbs)
//			.send()
//		);
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
		Request request = new Request.Builder()
			.url(baseURL.toString() + dbName)
			.get()
			.build();

		JsonNode node = null;
		try {
			Response response = client.newCall(request).execute();
			node = json(response.body().string());
		} catch (IOException e) {
			e.printStackTrace();
		}

//		JsonNode node = null;
//		try {
//			node = json(http.method("GET").to(dbName).send());
//		} catch (HTTPException e) {
//			return false;
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

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

	public JsonNode content(String path) {
		Request request = new Request.Builder()
			.url(baseURL.toString() + path)
			.get()
			.build();
		
		Response response = null;
		try {
			 response = client.newCall(request).execute();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		try {
			return json(response.body().string());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public JsonNode put(String path, String data) {
		Request request = new Request.Builder()
			.url(baseURL.toString() + path)
			.put(RequestBody.create(JSON, data))
			.build();

		Response response = null;
		try {
			 response = client.newCall(request).execute();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		try {
			return json(response.body().string());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public JsonNode delete(String path) {
		Request request = new Request.Builder()
			.url(baseURL.toString() + path)
			.delete()
			.build();

		Response response = null;
		try {
			 response = client.newCall(request).execute();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		try {
			return json(response.body().string());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
