package io.github.ztmark;

import java.util.LinkedHashMap;
import java.util.Map;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;

/**
 * Author: Mark
 * Date  : 2017/11/24
 */
public class MyFirstVerticle extends AbstractVerticle {

    // Store our product
    private Map<Integer, Whisky> products = new LinkedHashMap<>();
    // Create some product
    private void createSomeData() {
        Whisky bowmore = new Whisky("Bowmore 15 Years Laimrig", "Scotland, Islay");
        products.put(bowmore.getId(), bowmore);
        Whisky talisker = new Whisky("Talisker 57° North", "Scotland, Island");
        products.put(talisker.getId(), talisker);
    }


    @Override
    public void start(Future<Void> startFuture) throws Exception {

        // non vertx-web version
        /*vertx.createHttpServer()
             .requestHandler(req -> {
                 req.response().end("<h1>Hello from my first Vert.x 3 application</h1>");
             })
             .listen(config().getInteger("http.port", 8080), result -> {
                 if (result.succeeded()) {
                     startFuture.complete();
                 } else {
                     startFuture.fail(result.cause());
                 }
             });*/


        // vertx-web version

        createSomeData();

        final Router router = Router.router(vertx);

        router.route("/").handler(routingContext -> {
            final HttpServerResponse response = routingContext.response();
            response
                    .putHeader("content-type", "text/html")
                    .end("<h1>Hello from my first Vert.x 3 application</h1>");
        });

        router.route("/assets/*").handler(StaticHandler.create("assets"));

        router.route("/api/whiskies").handler(this::getAll);

        router.route("/api/whiskies*").handler(BodyHandler.create());
        router.post("/api/whiskies").handler(this::addOne);

        router.delete("/api/whiskies/:id").handler(this::deleteOne);
        router.get("/api/whiskies/:id").handler(this::getOne);
        router.put("/api/whiskies/:id").handler(this::updateOne);

        vertx.createHttpServer()
             .requestHandler(router::accept)
             .listen(config().getInteger("http.port", 8080), result -> {
                 if (result.succeeded()) {
                     startFuture.complete();;
                 } else {
                     startFuture.fail(result.cause());
                 }
             });

    }

    private void deleteOne(RoutingContext routingContext) {
        final String id = routingContext.request().getParam("id");
        if (id == null) {
            routingContext.response().setStatusCode(400).end();
        } else {
            products.remove(Integer.valueOf(id));
        }
        routingContext.response().setStatusCode(204).end();
    }

    private void addOne(RoutingContext routingContext) {
        final Whisky whisky = Json.decodeValue(routingContext.getBodyAsString(), Whisky.class);
        products.put(whisky.getId(), whisky);
        routingContext.response()
                      .setStatusCode(201)
                      .putHeader("content-type", "application/json; charset=utf-8")
                      .end(Json.encodePrettily(whisky));
    }

    private void getAll(RoutingContext routingContext) {
        routingContext.response()
                      .putHeader("content-type", "application/json; charset=utf-8")
                      .end(Json.encodePrettily(products.values()));
    }

    private void getOne(RoutingContext routingContext) {
        final String id = routingContext.request().getParam("id");
        if (id == null) {
            routingContext.response().setStatusCode(400).end();
        } else {
            final Integer idAsInteger = Integer.valueOf(id);
            Whisky whisky = products.get(idAsInteger);
            if (whisky == null) {
                routingContext.response().setStatusCode(404).end();
            } else {
                routingContext.response()
                              .putHeader("content-type", "application/json; charset=utf-8")
                              .end(Json.encodePrettily(whisky));
            }
        }
    }

    private void updateOne(RoutingContext routingContext) {
        final String id = routingContext.request().getParam("id");
        JsonObject json = routingContext.getBodyAsJson();
        if (id == null || json == null) {
            routingContext.response().setStatusCode(400).end();
        } else {
            final Integer idAsInteger = Integer.valueOf(id);
            Whisky whisky = products.get(idAsInteger);
            if (whisky == null) {
                routingContext.response().setStatusCode(404).end();
            } else {
                whisky.setName(json.getString("name"));
                whisky.setOrigin(json.getString("origin"));
                routingContext.response()
                              .putHeader("content-type", "application/json; charset=utf-8")
                              .end(Json.encodePrettily(whisky));
            }
        }
    }
}
