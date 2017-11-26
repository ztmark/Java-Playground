package io.github.ztmark;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

/**
 * Author: Mark
 * Date  : 2017/11/26
 */
public class Main {

    public static void main(String[] args) {
        final DeploymentOptions options = new DeploymentOptions()
                .setConfig(new JsonObject()
                        .put("http.port", 8086)
                        .put("url", "jdbc:hsqldb:mem:test?shutdown=true")
                        .put("driver_class", "org.hsqldb.jdbcDriver"));
        final Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(MyFirstVerticle.class.getName(), options);
    }

}
