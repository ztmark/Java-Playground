package io.github.ztmark;

import java.util.List;
import java.util.stream.Collectors;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.sql.SQLOptions;
import io.vertx.ext.sql.UpdateResult;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;

/**
 * Author: Mark
 * Date  : 2017/11/24
 */
public class MyFirstVerticle extends AbstractVerticle {

    private JDBCClient jdbcClient;

    @Override
    public void start(Future<Void> startFuture) throws Exception {

        jdbcClient = JDBCClient.createShared(vertx, config(), "My-Whisky-Collection");

        startBackend(connection -> {
            createSomeData(connection, nothing -> startWebApp(http -> completeStartup(http, startFuture)), startFuture);
        }, startFuture);
    }

    private void startBackend(Handler<AsyncResult<SQLConnection>> next, Future<Void> future) {
        jdbcClient.getConnection(ar -> {
            if (ar.failed()) {
                future.fail(ar.cause());
            } else {
                next.handle(Future.succeededFuture(ar.result()));
            }
        });
    }

    private void startWebApp(Handler<AsyncResult<HttpServer>> next) {
        final Router router = Router.router(vertx);

        router.route("/").handler(routingContext -> {
            final HttpServerResponse response = routingContext.response();
            response
                    .putHeader("content-type", "text/html")
                    .end("<h1>Hello from my first Vert.x 3 application</h1>");
        });

        router.route("/assets/*").handler(StaticHandler.create("assets"));

        router.get("/api/whiskies").handler(this::getAll);

        router.route("/api/whiskies*").handler(BodyHandler.create());

        router.post("/api/whiskies").handler(this::addOne);
        router.delete("/api/whiskies/:id").handler(this::deleteOne);
        router.get("/api/whiskies/:id").handler(this::getOne);
        router.put("/api/whiskies/:id").handler(this::updateOne);

        vertx.createHttpServer()
             .requestHandler(router::accept)
             .listen(config().getInteger("http.port", 8080), next);
    }

    private void completeStartup(AsyncResult<HttpServer> http, Future<Void> future) {
        if (http.succeeded()) {
            future.complete();
        } else {
            future.fail(http.cause());
        }
    }

    private void createSomeData(AsyncResult<SQLConnection> result, Handler<AsyncResult<Void>> next, Future<Void> future) {
        if (result.failed()) {
            future.fail(result.cause());
        } else {
            final SQLConnection connection = result.result();
            connection.execute("CREATE TABLE IF NOT EXISTS Whisky (id INTEGER IDENTITY, name varchar(100), origin varchar(100))",
                    ar -> {
                        if (ar.failed()) {
                            future.fail(ar.cause());
                            connection.close();
                            return;
                        }
                        connection.query("SELECT * FROM Whisky", select -> {
                            if (select.failed()) {
                                future.fail(select.cause());
                                connection.close();
                                return;
                            }
                            if (select.result().getNumRows() == 0) {
                                insert(new Whisky("Bowmore 15 Years Laimrig", "Scotland, Islay"), connection,
                                        v -> insert(new Whisky("Talisker 57° North", "Scotland, Island"), connection,
                                                r -> {
                                                    next.handle(Future.succeededFuture());
                                                    connection.close();
                                                }));
                            } else {
                                next.handle(Future.succeededFuture());
                                connection.close();
                            }
                        });
                    });
        }
    }

    private void deleteOne(RoutingContext routingContext) {
        final String id = routingContext.request().getParam("id");
        if (id == null) {
            routingContext.response().setStatusCode(400).end();
            return;
        }
        jdbcClient.getConnection(ar -> {
            final SQLConnection connection = ar.result();
            connection.updateWithParams("DELETE FROM Whisky WHERE id=?", new JsonArray().add(id), result -> {
                routingContext.response().setStatusCode(204).end();
                connection.close();
            });
        });
    }

    private void addOne(RoutingContext routingContext) {
        jdbcClient.getConnection(ar -> {
            final Whisky whisky = Json.decodeValue(routingContext.getBodyAsString(), Whisky.class);
            final SQLConnection connection = ar.result();
            insert(whisky, connection, next -> {
                routingContext.response()
                              .setStatusCode(201)
                              .putHeader("content-type", "application/json; charset=utf-8")
                              .end(Json.encodePrettily(next.result()));

            });
        });
    }

    private void getAll(RoutingContext routingContext) {
        jdbcClient.getConnection(ar -> {
            final SQLConnection connection = ar.result();
            connection.query("SELECT * FROM Whisky", result -> {
                final List<Whisky> whiskies = result.result().getRows().stream().map(Whisky::new).collect(Collectors.toList());
                routingContext.response()
                              .putHeader("content-type", "application/json; charset=utf-8")
                              .end(Json.encodePrettily(whiskies));
                connection.close();
            });
        });
    }

    private void getOne(RoutingContext routingContext) {
        final String id = routingContext.request().getParam("id");
        if (id == null) {
            routingContext.response().setStatusCode(400).end();
        } else {
            jdbcClient.getConnection(ar -> {
                select(id, ar.result(), result -> {
                    if (result.succeeded()) {
                        routingContext.response()
                                      .putHeader("content-type", "application/json; charset=utf-8")
                                      .end(Json.encodePrettily(result.result()));

                    } else {
                        routingContext.response().setStatusCode(404).end();
                    }
                });
            });
        }
    }

    private void updateOne(RoutingContext routingContext) {
        final String id = routingContext.request().getParam("id");
        JsonObject json = routingContext.getBodyAsJson();
        if (id == null || json == null) {
            routingContext.response().setStatusCode(400).end();
        } else {

            jdbcClient.getConnection(ar -> {
                update(id, json, ar.result(), result -> {
                    if (result.failed()) {
                        routingContext.response().setStatusCode(404).end();
                    } else {
                        routingContext.response()
                                      .putHeader("content-type", "application/json; charset=utf-8")
                                      .end(Json.encodePrettily(result.result()));
                    }
                    ar.result().close();
                });
            });
        }
    }

    private void insert(Whisky whisky, SQLConnection connection, Handler<AsyncResult<Whisky>> next) {
        String sql = "INSERT INTO Whisky (name, origin) VALUES ?, ?";
        // 需要设置 AutoGeneratedKeys 为 true result.getKeys() 才有值
        connection.setOptions(new SQLOptions().setAutoGeneratedKeys(true)).updateWithParams(sql, new JsonArray().add(whisky.getName()).add(whisky.getOrigin()),
                ar -> {
                    if (ar.failed()) {
                        next.handle(Future.failedFuture(ar.cause()));
                        return;
                    }
                    final UpdateResult result = ar.result();
                    Whisky w = new Whisky(result.getKeys().getInteger(0), whisky.getName(), whisky.getOrigin());
                    next.handle(Future.succeededFuture(w));
                });
    }

    private void select(String id, SQLConnection connection, Handler<AsyncResult<Whisky>> resultHandler) {
        connection.queryWithParams("SELECT * FROM Whisky WHERE id=?", new JsonArray().add(id), result -> {
            if (result.failed()) {
                resultHandler.handle(Future.failedFuture("Whisky not found"));
            } else {
                if (result.result().getNumRows() >= 1) {
                    resultHandler.handle(Future.succeededFuture(new Whisky(result.result().getRows().get(0))));
                } else {
                    resultHandler.handle(Future.failedFuture("Whisky not found"));
                }
            }
        });
    }

    private void update(String id, JsonObject jsonObject, SQLConnection connection, Handler<AsyncResult<Whisky>> resultHandle) {
        connection.updateWithParams("UPDATE Whisky SET name=?, origin=? WHERE id=?",
                new JsonArray().add(jsonObject.getString("name")).add(jsonObject.getString("origin")).add(id), update -> {
                    if (update.failed()) {
                        resultHandle.handle(Future.failedFuture("Cannot update the whisky"));
                        return;
                    }
                    if (update.result().getUpdated() == 0) {
                        resultHandle.handle(Future.failedFuture("Whisky not found"));
                        return;
                    }
                    resultHandle.handle(Future.succeededFuture(
                            new Whisky(Integer.valueOf(id), jsonObject.getString("name"), jsonObject.getString("origin"))));
                });
    }
}
