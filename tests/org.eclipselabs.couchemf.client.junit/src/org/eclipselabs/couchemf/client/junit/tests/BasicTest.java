package org.eclipselabs.couchemf.client.junit.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.eclipselabs.couchemf.client.CouchClient;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

public class BasicTest {

	@Test
	public void testConnectionToDefaultServer() throws JsonProcessingException, IOException {
		CouchClient client = new CouchClient.Builder().build();

		assertTrue(client.isConnected());
	}

	public void testConnectionToFakeServer() throws JsonProcessingException, IOException {
		CouchClient client = new CouchClient.
				Builder().
				url("http://127.0.0.1:1334").
				build();

		assertFalse(client.isConnected());
	}
	
	@Test
	public void testRetrieveAllDbs() throws IOException {
		CouchClient client = new CouchClient.Builder().build();
		
		JsonNode node = client.dbs();
		assertNotNull(node);
		assertTrue(node.isArray());
	}
	
	@Test
	public void testRetrieveDbInfo() throws IOException {
		CouchClient client = new CouchClient.Builder().build();		
		JsonNode node = client.db("sample");
		
		assertNotNull(node);
		assertTrue(node.isObject());
		assertTrue(node.has("db_name"));
		assertEquals("sample", node.get("db_name").asText());
	}

	@Test
	public void testConnectNonExistingDatabaseException() throws JsonProcessingException, IOException {
		CouchClient client = new CouchClient.Builder().build();
		JsonNode result = client.db("fake");
		
		assertTrue(result.has("error"));
	}

	@Test
	public void testConnectNonExistingDatabase() throws JsonProcessingException, IOException {
		CouchClient client = new CouchClient.Builder().build();
		assertFalse(client.hasDatabase("fake"));
	}

	@Test
	public void testCreateAndDeleteDatabase() throws JsonProcessingException, IOException {
		CouchClient client = new CouchClient.Builder().build();
		assertFalse(client.hasDatabase("fake"));

		JsonNode result = client.createDatabase("fake");
		assertTrue(result.has("ok"));
		assertEquals("true", result.get("ok").asText());

		assertTrue(client.hasDatabase("fake"));

		client.deleteDatabase("fake");
		assertFalse(client.hasDatabase("fake"));
	}

	@Test
	public void testCreateAndDeleteDocument() throws JsonProcessingException, IOException {
		CouchClient client = new CouchClient.Builder().build();
		assertFalse(client.hasDatabase("fake"));
		client.createDatabase("fake");
		assertTrue(client.hasDatabase("fake"));

		String data = "{\"hello\":\"world\"}";

		JsonNode result = client.createDocument("fake", "test", data);		
		assertTrue(result.has("ok"));
		assertTrue(result.get("ok").asBoolean());
		assertTrue(result.has("rev"));

		String rev = result.get("rev").asText();

		result = client.deleteDocument("fake", "test", rev);		
		assertTrue(result.has("ok"));
		assertTrue(result.get("ok").asBoolean());

		client.deleteDatabase("fake");
		assertFalse(client.hasDatabase("fake"));
	}

}
