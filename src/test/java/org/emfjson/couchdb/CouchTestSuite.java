package org.emfjson.couchdb;

import org.emfjson.couchdb.tests.ClientTest;
import org.emfjson.couchdb.tests.LoadDocumentTest;
import org.emfjson.couchdb.tests.StoreDocumentTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	ClientTest.class,
	LoadDocumentTest.class,
	StoreDocumentTest.class
})
public class CouchTestSuite {}
