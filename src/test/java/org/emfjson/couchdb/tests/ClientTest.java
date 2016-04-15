package org.emfjson.couchdb.tests;

import com.fasterxml.jackson.databind.JsonNode;
import org.emfjson.couchdb.client.CouchClient;
import org.emfjson.couchdb.client.CouchDocument;
import org.emfjson.couchdb.client.DB;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;

import static org.junit.Assert.*;

public class ClientTest {

	private CouchClient client;

	@Before
	public void setUp() {
		client = new CouchClient();
	}

	@Test
	public void testConnectionToDefaultServer() throws IOException {
		assertTrue(client.isConnected());
	}

	@Test(expected = ConnectException.class)
	public void testConnectionToFakeServer() throws IOException {
		URL url = new URL("http://127.0.0.1:1334");
		CouchClient client = new CouchClient(url);
		assertFalse(client.isConnected());
	}

	@Test
	public void testRetrieveAllDbs() throws IOException {
		JsonNode node = client.dbs();
		assertNotNull(node);
		assertTrue(node.isArray());
	}

	@Test
	public void testRetrieveDbInfo() throws IOException {
		DB db = client.db("sample");

		assertFalse(db.exist());
		db.create();
		assertTrue(db.exist());

		JsonNode node = db.info();
		assertNotNull(node);
		assertTrue(node.isObject());
		assertTrue(node.has("db_name"));
		assertEquals("sample", node.get("db_name").asText());

		db.delete();
	}

	@Test
	public void testConnectNonExistingDatabaseException() throws IOException {
		DB db = client.db("fake");
		JsonNode result = db.info();
		
		assertTrue(result.has("error"));
	}

	@Test
	public void testConnectNonExistingDatabase() throws IOException {
		assertFalse(client.hasDatabase("fake"));
	}

	@Test
	public void testCreateAndDeleteDatabase() throws IOException {
		assertFalse(client.hasDatabase("fake"));

		DB db = client.db("fake");
		
		JsonNode result = db.create();
		assertTrue(result.has("ok"));
		assertEquals("true", result.get("ok").asText());

		assertTrue(client.hasDatabase("fake"));
		assertTrue(db.exist());

		db.delete();
		assertFalse(client.hasDatabase("fake"));
		assertFalse(db.exist());
	}

	@Test
	public void testCreateAndDeleteDocument() throws IOException {
		assertFalse(client.hasDatabase("fake"));

		DB db = client.db("fake");
		db.create();
		assertTrue(db.exist());

		String data = "{\"hello\":\"world\"}";

		CouchDocument doc = db.doc("test");
		JsonNode result = doc.create(data);

		assertTrue(result.has("ok"));
		assertTrue(result.get("ok").asBoolean());
		assertTrue(result.has("rev"));

		String rev = result.get("rev").asText();

		result = doc.delete(rev);		
		assertTrue(result.has("ok"));
		assertTrue(result.get("ok").asBoolean());

		db.delete();
		assertFalse(client.hasDatabase("fake"));
	}

}
