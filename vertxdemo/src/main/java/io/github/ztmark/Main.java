package io.github.ztmark;


import io.vertx.core.Vertx;

/**
 * Author: Mark
 * Date  : 2017/11/21
 */
public class Main {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(FirstVerticle.class.getName());
    }

}
