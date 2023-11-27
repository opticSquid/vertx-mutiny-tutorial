package com.hangout.experiment.vertx_mutiny_tutorial;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.smallrye.mutiny.Uni;

public class MutinyUni {
    private static final Logger log = LoggerFactory.getLogger(MutinyUni.class);

    public static void main(String[] args) {
        // Uni is a stream that can represent either an item or a failure
        Uni.createFrom().item("hello").onItem().transform(item -> item + " Mutiny!").onItem()
                .transform(String::toUpperCase).subscribe().with(item -> log.debug("Item: {}", item));
        Uni.createFrom().item("Ignored due to failure").onItem().castTo(Integer.class).subscribe()
                .with(item -> log.info("Item: {}", item), failure -> log.error("Failure: {}", failure.getMessage()));
    }
}
