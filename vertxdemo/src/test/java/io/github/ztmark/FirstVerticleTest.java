package io.github.ztmark;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

/**
 * Author: Mark
 * Date  : 2017/11/21
 */
@RunWith(VertxUnitRunner.class)
public class FirstVerticleTest {

    private Vertx vertx;

    @Before
    public void setUp(TestContext testContext) {
        vertx = Vertx.vertx();
        vertx.deployVerticle(FirstVerticle.class.getName(), testContext.asyncAssertSuccess());
    }

    @After
    public void tearDown(TestContext testContext) {
        vertx.close(testContext.asyncAssertSuccess());
    }

    @Test
    public void testApplication(TestContext testContext) {
        final Async async = testContext.async();
        vertx.createHttpClient().getNow(8080, "localhost", "/", response -> {
            response.handler(body -> {
                testContext.assertTrue(body.toString().equals("Hello World!"));
                async.complete();
            });
        });
    }


}