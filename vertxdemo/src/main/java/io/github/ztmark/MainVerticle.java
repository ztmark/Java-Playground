package io.github.ztmark;

import io.vertx.core.AbstractVerticle;

/**
 * Author: Mark
 * Date  : 2017/11/21
 */
public class MainVerticle extends AbstractVerticle {

    @Override
    public void start() {
        vertx.deployVerticle(FirstVerticle.class.getName());
//        vertx.createHttpServer()
//             .requestHandler(req -> req.response().end("Hello Vert.x!"))
//             .listen(8080);
    }

}
