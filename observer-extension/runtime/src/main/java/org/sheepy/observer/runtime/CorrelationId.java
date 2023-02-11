package org.sheepy.observer.runtime;

import javax.inject.Singleton;

@Singleton
public class CorrelationId {

    int correlationId;

    public int getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(int correlationId) {
        this.correlationId = correlationId;
    }
}
