package io.vertx.microservice;

import com.couchbase.client.java.Scope;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.pgclient.PgConnectOptions;

import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;

import java.util.logging.Logger;

public class MainVerticle extends AbstractVerticle {
    public static final Logger logger = Logger.getLogger(MainVerticle.class.getSimpleName());

    @Override
    public void start(Promise startPromise) {
        logger.info("Starting Http Server .... ");
        Scope cbBlogScope = new CouchbaseService().getScope("post_service");

        PostRepository postRepository = PostRepository.create(cbBlogScope);
        PostHandler postHandler = PostHandler.create(postRepository);

        Router router = routes(postHandler);

        vertx.createHttpServer()
                .requestHandler(router)
                .listen(8888)
                .onSuccess( server -> {
                    startPromise.complete();
                    logger.info("server running on port :" + server.actualPort());
                })
                .onFailure(error -> {
                    startPromise.fail(error);
                    logger.warning("Server start failed");
                });
    }

    private PgPool pgPool() {
        PgConnectOptions connectOptions = new PgConnectOptions()
                .setPort(5432)
                .setHost("localhost")
                .setDatabase("blogdb")
                .setUser("user")
                .setPassword("password");
        PoolOptions poolOptions = new PoolOptions()
                .setMaxSize(5);

        PgPool pool = PgPool.pool(vertx, connectOptions, poolOptions);
        return pool;
    }

    private Router routes(PostHandler postHandler) {
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        router.get("/posts").produces("application/json").handler(postHandler::all);
        router.get("/posts/:id").produces("application/json").handler(postHandler::get).failureHandler(rc -> rc.response().setStatusCode(404).end());
        router.post("/posts").consumes("application/json").handler(postHandler::save);
        return router;
    }
}
