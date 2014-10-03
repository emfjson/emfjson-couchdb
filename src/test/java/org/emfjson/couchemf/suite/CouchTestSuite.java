package org.emfjson.couchemf.suite;

import org.emfjson.couchemf.tests.BasicTest;
import org.emfjson.couchemf.tests.LoadDocumentTest;
import org.emfjson.couchemf.tests.StoreDocumentTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	BasicTest.class,
	LoadDocumentTest.class,
	StoreDocumentTest.class
})
public class CouchTestSuite {}
