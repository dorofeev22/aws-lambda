package com.novardis.train;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import org.bson.UuidRepresentation;
import org.mongojack.JacksonMongoCollection;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;

public class MongoDao<T> {

    protected MongoCollection<T> table;
    private static MongoClient mongoClient;
    private final String DB_NAME;

    public MongoDao() {
        throw new RuntimeException("not implemented");
    }

    public MongoDao(String tableName) {
        DB_NAME = tableName;
        try {
            createClient();
            this.table = JacksonMongoCollection.builder().build(mongoClient, DB_NAME, DB_NAME, getDocumentClass(), UuidRepresentation.JAVA_LEGACY);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    private void createClient() {
        if (mongoClient != null) {
            var settings = MongoClientSettings.builder();
            settings.applyToClusterSettings((cluster) -> cluster.hosts(Collections.singletonList(new ServerAddress("localhost", 27017))));
            settings.credential(MongoCredential.createCredential("tui", DB_NAME, "tui".toCharArray()));
            mongoClient = MongoClients.create(settings.build());
        }
    }

    public void add(final T newDocument) {
        this.table.insertOne(newDocument);
    }

    private Class<T> getDocumentClass() throws ClassNotFoundException {
        Type type = getClass().getGenericSuperclass();
        String className = ((ParameterizedType) type).getActualTypeArguments()[0].getTypeName();
        return (Class<T>) Class.forName(className);
    }
}
