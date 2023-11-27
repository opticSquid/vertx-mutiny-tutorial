package com.hangout.experiment.vertx_mutiny_tutorial;

import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.smallrye.mutiny.Multi;

public class MutinyMulti {
    private static final Logger log = LoggerFactory.getLogger(MutinyMulti.class);

    public static void main(String[] args) {
        // Multi represents a stream of elements. It can represent 0, 1, n or an
        // infinite number of items.
        Multi.createFrom().items(IntStream.rangeClosed(0, 10).boxed()).onItem()
                .transform(value -> value % 2 == 0 ? value / 0 : value * 3 + 1).onItem()
                .transform(String::valueOf)
                // just subscribe to the last 2 entries
                .select().last(4)
                .subscribe().with(item -> log.info("Item: {}", item));
    }
}
