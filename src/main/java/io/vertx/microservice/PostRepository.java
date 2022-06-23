package io.vertx.microservice;

import com.couchbase.client.java.Scope;
import com.couchbase.client.java.kv.GetResult;
import com.couchbase.client.java.query.QueryResult;

import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Logger;

public class PostRepository {
    public static final Logger logger = Logger.getLogger(PostRepository.class.getSimpleName());
    private final Scope client;

    private PostRepository(Scope client) {
        this.client = client;
    }

    public static PostRepository create(Scope cbClient) {
        return new PostRepository(cbClient);
    }

    public List<Post> findAll() {
       QueryResult queryResult = client.query("select blog.* from document.post_service.blog");
       return queryResult.rowsAs(Post.class);
    }

    public Post findById(String id) {
        Objects.requireNonNull(id, "Id can not be null");
        GetResult getResult = client.collection("blog").get(id.toString());
        return getResult.contentAs(Post.class);
    }

    public String save(Post data) {
        String id = "BLOG:" + UUID.randomUUID();
        LocalDateTime createdTime = LocalDateTime.now();
        data.setId(id);
        data.setCreatedAt(createdTime);
        client.collection("blog")
                .upsert(id, data);
        return id;

    }
}
