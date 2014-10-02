package org.emfjson.couchemf.client;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

class HttpClient {

	private URL url = null;

	HttpClient(URL url) {
		this.url = url;
	}

	public SendMethod send(String method) throws IOException {
		return send(method, null);
	}

	public SendMethod send(String method, String data) throws IOException {
		return new SendMethod(method, data);
	}

	private static String readData(HttpURLConnection connection) throws IOException {
		final StringBuffer result = new StringBuffer();
		InputStream stream = null;
		BufferedReader reader = null;

		try {
			try {
				stream = connection.getInputStream();
			} catch(FileNotFoundException e) {
				stream = connection.getErrorStream();
			} catch (IOException e) {
				stream = connection.getErrorStream();
			}

			reader = new BufferedReader(new InputStreamReader(stream));

			if (reader != null) {
				String line = reader.readLine();

				while(line != null) {
					result.append(line);
					line = reader.readLine();
				}
			}
		} finally {
			if (stream != null) stream.close();
			if (reader != null) reader.close();
		}

		return result.length() == 0 ? null : result.toString();
	}

	private static void writeData(HttpURLConnection connection, String data) {
		connection.setDoOutput(true);
		OutputStreamWriter writer = null;
		try {
			writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
			writer.write(data);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null)
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	private HttpURLConnection open(URL service, String method) throws IOException {
		final HttpURLConnection connection = (HttpURLConnection) service.openConnection();
		if (connection != null) connection.setRequestMethod(method);
		return connection;
	}

	public static URL baseURL(URL url) throws MalformedURLException, URISyntaxException {
		if (url.getPath().isEmpty()) {
			return url;
		} else {
			return new URL(new URI(
					url.getProtocol(), 
					url.getUserInfo(),
					url.getHost(),
					url.getPort(),
					"",
					url.getQuery(),
					url.getRef()).toASCIIString());
		}
	}

	public static URL url(URL baseURL, String path, String query) throws MalformedURLException, URISyntaxException {
		return new URL(new URI(
				baseURL.getProtocol(), 
				baseURL.getUserInfo(),
				baseURL.getHost(),
				baseURL.getPort(),
				path == null ? "" : path.startsWith("/") ? path : "/" + path,
				query == null ? baseURL.getQuery() : query,
				baseURL.getRef()).toASCIIString());
	}
	
	public class SendMethod {
		
		private final String method;
		private final String data;		
		private String path = null;
		private String query = null;

		private SendMethod(String method) {
			this(method, null);
		}

		private SendMethod(String method, String data) {
			this.method = method;			
			this.data = data;
		}

		public SendMethod to(String path) {
			this.path = path;
			return this;
		}

		public SendMethod q(String query) {
			this.query = query;
			return this;
		}

		public String execute() throws IOException {
			String result = null;
			URL service = null;
			try {
				service = url(url, path, query);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}

			if (service != null) {
				HttpURLConnection connection = open(service, method);
				connection.setRequestProperty("Content-Type", "application/json");
				connection.setRequestProperty("Accept", "application/json");

				if (data != null) writeData(connection, data);
				result = readData(connection);
			}

			return result;
		}
	}
}