package io.vertx.microservice;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Scope;

public class CouchbaseService {
    private static final String connectionString = "couchbase://localhost";
    private static final String username = "Administrator";
    private static final String password = "couchbase";
    private static final String bucketName = "document";

    private final Cluster cluster;

    public CouchbaseService() {
        cluster = Cluster.connect(connectionString, username, password);
    }
    public Scope getScope(String scopeName) {
        Bucket bucket = cluster.bucket(bucketName);
        return  bucket.scope(scopeName);
    }
}
