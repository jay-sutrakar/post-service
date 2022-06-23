import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.Scope;
import com.couchbase.client.java.kv.MutationResult;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class CouchbaseTesting {
    static String connectionString = "couchbase://localhost";
    static String username = "Administrator";
    static String password = "couchbase";
    static String bucketName = "document";

    @Test
    void testConnection() {
        Cluster cluster = Cluster.connect(connectionString, username, password);
        Bucket bucket = cluster.bucket(bucketName);
        Scope scope = bucket.scope("post-service");
        Collection collection = scope.collection("blogs");
        MutationResult mutationResult = collection.upsert("BLOG:" + UUID.randomUUID(), new JsonObject().put("title", "first post").put("content","reactive programming"));
        System.out.println(mutationResult.toString());

    }
}
