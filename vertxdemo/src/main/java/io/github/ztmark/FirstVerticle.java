package io.github.ztmark;

import io.vertx.core.AbstractVerticle;

/**
 * Author: Mark
 * Date  : 2017/11/21
 */
public class FirstVerticle extends AbstractVerticle {

    public void start() {
        vertx.createHttpServer().requestHandler(req -> {
            req.response().putHeader("content-type", "text/plain").end("Hello World!");
        }).listen(8080);
    }

}
