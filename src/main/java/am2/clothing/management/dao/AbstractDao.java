package am2.clothing.management.dao;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoDatabase;

public abstract class AbstractDao {
    private static final String dbs = "AM2";
    protected MongoDatabase db;
    private MongoClient client;

    public AbstractDao(MongoClient client) {
        super();
        this.client = client;
        this.db = client.getDatabase(dbs);
    }

    public MongoClient getClient() {
        return client;
    }

    public MongoDatabase getDb() {
        return db;
    }


}