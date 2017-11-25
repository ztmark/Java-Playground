package io.github.ztmark;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

/**
 * Author: Mark
 * Date  : 2017/11/24
 */
public class MyFirstVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        vertx.createHttpServer()
             .requestHandler(req -> {
                 req.response().end("<h1>Hello from my first Vert.x 3 application</h1>");
             })
             .listen(config().getInteger("http.port", 8080), result -> {
                 if (result.succeeded()) {
                     startFuture.complete();
                 } else {
                     startFuture.fail(result.cause());
                 }
             });
    }
}
