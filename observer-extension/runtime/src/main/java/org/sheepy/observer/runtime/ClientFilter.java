package org.sheepy.observer.runtime;

import jakarta.inject.Inject;
import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;

public class ClientFilter implements ClientRequestFilter {
    @Inject
    CorrelationId correlationId;

    @Override
    public void filter(ClientRequestContext requestContext) {
        System.out.println("Adding correlation headers: " + correlationId.getCorrelationId());

        requestContext.getHeaders().add(Filter.X_SHEEPY_CORRELATION_ID, correlationId.getCorrelationId());
    }
}
