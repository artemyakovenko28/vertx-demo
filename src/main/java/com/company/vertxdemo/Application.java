package com.company.vertxdemo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import java.time.LocalDate;

public class Application extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        Router router = Router.router(vertx);
        router.get("/api/baeldung/articles/:id").handler(this::getArticle);

        vertx.createHttpServer().requestHandler(router::accept)
                .listen(8080, result -> {
                   if (result.succeeded()) {
                       startFuture.complete();
                   } else {
                       startFuture.fail(startFuture.cause());
                   }
                });
    }

    private void getArticle(RoutingContext routingContext) {
        String id = routingContext.request().getParam("id");
        Article article = new Article(id, "This is a test article", "Yakovenko",
                LocalDate.now().toString(), 5);
        routingContext.response()
                .putHeader("Content-Type", "application/json")
                .setStatusCode(200)
                .end(Json.encodePrettily(article));
    }
}
