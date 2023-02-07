package org.sheepy.observer;

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;

public class Interaction extends ReactivePanacheMongoEntity {
    private String name;

    public Interaction() {
    }

    public Interaction(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
