package org.sheepy.observer;

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;

public class Component extends ReactivePanacheMongoEntity {
    private String name;

    public Component() {
    }

    public Component(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
