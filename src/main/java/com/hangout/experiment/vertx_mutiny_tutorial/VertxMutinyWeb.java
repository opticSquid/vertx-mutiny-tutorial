package com.hangout.experiment.vertx_mutiny_tutorial;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.web.Router;
import io.vertx.mutiny.ext.web.RoutingContext;

public class VertxMutinyWeb extends AbstractVerticle {
    private static final Logger log = LoggerFactory.getLogger(VertxMutinyWeb.class);

    public static void main(String[] args) {

        Vertx.vertx()
                .deployVerticle(new VertxMutinyWeb())
                // ! If I deploy mmultiple instances also it still works
                // .deployVerticle("com.hangout.experiment.vertx_mutiny_tutorial.VertxMutinyWeb",
                // new DeploymentOptions().setInstances(4))
                .subscribe()
                .with(id -> log.info("Verticle started with id: {}", id));
    }

    @Override
    public Uni<Void> asyncStart() {
        var router = Router.router(vertx);
        router.route().failureHandler(this::failureHandler);
        router.get("/users").respond(this::getUsers);
        return vertx.createHttpServer()
                .requestHandler(router).listen(8111).replaceWithVoid();
    }

    private void failureHandler(RoutingContext rc) {
        rc.response().setStatusCode(500).endAndForget("Something went wrong!");
    }

    private Uni<JsonArray> getUsers(RoutingContext rc) {
        var responseBody = new JsonArray();
        responseBody.add(new JsonObject().put("name", "Aburame").put("role", "insect-masters"));
        responseBody.add(new JsonObject().put("name", "Ucchiha").put("role", "shringan"));
        return Uni.createFrom().item(responseBody);
    }
}
