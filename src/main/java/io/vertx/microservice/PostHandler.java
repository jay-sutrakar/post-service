package io.vertx.microservice;

import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

import java.util.List;
import java.util.logging.Logger;

public class PostHandler {
    public static final Logger logger = Logger.getLogger(PostHandler.class.getSimpleName());
    PostRepository posts;

    private PostHandler(PostRepository postRepository) {
        this.posts = postRepository;
    }

    public static PostHandler create(PostRepository posts) {
        return new PostHandler(posts);
    }

    public void all(RoutingContext rc) {
        var params = rc.queryParams();
        var q = params.get("q");
        List<Post> posts = this.posts.findAll();
        rc.response().end(Json.encode(posts));
    }

    public void get(RoutingContext rc) {
        String id = rc.pathParam("id");
        Post post = this.posts.findById(id);
        rc.response().end(Json.encode(post));
    }

    public void save(RoutingContext rc) {
        CreatePostCommand post = rc.body().asJsonObject().mapTo(CreatePostCommand.class);
        String id = this.posts.save(Post.of(post.getTitle(), post.getContent()));
        rc.response().end(id);
    }
}
