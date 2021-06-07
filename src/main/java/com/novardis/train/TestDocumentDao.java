package com.novardis.train;

public class TestDocumentDao extends MongoDao<TestDocument>{

    public TestDocumentDao() {
        super("test_document");
    }
}
