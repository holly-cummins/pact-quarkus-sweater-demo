package org.sheepy.observer.runtime;

public class Interaction {
    String methodName;
    String owningComponent;

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
}
