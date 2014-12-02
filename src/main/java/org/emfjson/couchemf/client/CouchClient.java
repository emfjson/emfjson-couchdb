package org.emfjson.couchemf.client;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

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
public class CouchClient {

	public final ObjectMapper mapper = new ObjectMapper();

	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	private final OkHttpClient client = new OkHttpClient();

	private final URL baseURL;

	public CouchClient(URL baseURL) {
		this.baseURL = baseURL;
	}

	public CouchClient() {
		this.baseURL = getDefault();
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
	 */
	public boolean isConnected() throws IOException {
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
	 */
	public JsonNode dbs() throws IOException {
		Request request = new Request.Builder()
			.url(baseURL.toString() + Constants._all_dbs)
			.get()
			.build();

		return call(request);
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
	
	/**
	 * Returns true if the CouchDB instance has this database.
	 * 
	 * @param dbName
	 * @return {@link Boolean}
	 * @throws IOException 
	 */
	public boolean hasDatabase(String dbName) throws IOException {
		Request request = new Request.Builder()
			.url(baseURL.toString() + dbName)
			.get()
			.build();

		JsonNode node = call(request);

		return node.has("db_name");
	}

	JsonNode json(String value) throws IOException {
		return value == null ? null : mapper.readTree(value);
	}

	public JsonNode content(String path) throws IOException {
		Request request = new Request.Builder()
			.url(baseURL.toString() + path)
			.get()
			.build();

		return call(request);
	}

	public JsonNode put(String path, String data) throws IOException {
		Request request = new Request.Builder()
			.url(baseURL.toString() + path)
			.put(RequestBody.create(JSON, data))
			.build();

		return call(request);
	}

	public JsonNode delete(String path) throws IOException {
		Request request = new Request.Builder()
			.url(baseURL.toString() + path)
			.delete()
			.build();

		return call(request);
	}

	private JsonNode call(Request request) throws IOException {
		Response response = client.newCall(request).execute();
		if (response != null) {
			return json(response.body().string());
		}
		return null;
	}

}
