package io.github.ztmark;

import io.vertx.core.Vertx;

/**
 * Author: Mark
 * Date  : 2017/11/26
 */
public class Main {

    public static void main(String[] args) {
        final Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(MyFirstVerticle.class.getName());
    }

}
