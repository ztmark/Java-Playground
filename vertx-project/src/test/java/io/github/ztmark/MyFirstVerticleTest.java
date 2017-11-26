package io.github.ztmark;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

/**
 * Author: Mark
 * Date  : 2017/11/24
 */
@RunWith(VertxUnitRunner.class)
public class MyFirstVerticleTest {

    private Vertx vertx;
    private int port = 8081;

    @Before
    public void setUp(TestContext context) throws IOException {
        final DeploymentOptions options = new DeploymentOptions().setConfig(new JsonObject().put("http.port", port));
        vertx = Vertx.vertx();
        vertx.deployVerticle(MyFirstVerticle.class.getName(), options, context.asyncAssertSuccess());
    }

    @After
    public void tearDown(TestContext context) {
        vertx.close(context.asyncAssertSuccess());
    }

    @Test
    public void testMyApplication(TestContext context) {
        final Async async = context.async();
        vertx.createHttpClient().getNow(port, "localhost", "/", response -> {
            response.handler(body -> {
                context.assertTrue(body.toString().equals("<h1>Hello from my first Vert.x 3 application</h1>"));
                async.complete();
            });
        });
    }

    @Test
    public void testCheckThatTheIndexPageIsServed(TestContext context) {
        final Async async = context.async();
        vertx.createHttpClient().getNow(port, "localhost", "/assets/index.html", response -> {
            context.assertEquals(200, response.statusCode());
            context.assertEquals("text/html;charset=UTF-8", response.getHeader("content-type"));
            response.bodyHandler(body -> {
                context.assertTrue(body.toString().contains("<title>My Whisky Collection</title>"));
                async.complete();
            });
        });
    }

    @Test
    public void testThatWeCanAdd(TestContext context) {
        final String json = Json.encodePrettily(new Whisky("test1", "test2"));
        final Async async = context.async();
        vertx.createHttpClient().post(port, "localhost", "/api/whiskies")
             .putHeader("content-type", "application/json")
             .putHeader("content-length", String.valueOf(json.length()))
             .handler(response -> {
                 context.assertEquals(201, response.statusCode());
                 context.assertTrue(response.getHeader("content-type").contains("application/json"));
                 response.bodyHandler(body -> {
                     final Whisky whisky = Json.decodeValue(body, Whisky.class);
                     context.assertEquals("test1", whisky.getName());
                     context.assertEquals("test2", whisky.getOrigin());
                     context.assertNotNull(whisky.getId());
                     async.complete();
                 });
             }).write(json).end();
    }

}