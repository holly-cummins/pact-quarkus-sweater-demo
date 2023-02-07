package org.sheepy.observer;

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;

public class Interaction extends ReactivePanacheMongoEntity {
    String methodName;
    String owningComponent;
    String payload;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getOwningComponent() {
        return owningComponent;
    }

    public void setOwningComponent(String owningComponent) {
        this.owningComponent = owningComponent;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
