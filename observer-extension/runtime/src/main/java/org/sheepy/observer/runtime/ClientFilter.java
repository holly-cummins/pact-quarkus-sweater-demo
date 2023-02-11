package org.sheepy.observer.runtime;

import javax.inject.Inject;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;

public class ClientFilter implements ClientRequestFilter {
    @Inject
    CorrelationId correlationId;

    @Override
    public void filter(ClientRequestContext requestContext) {
        System.out.println("Adding correlation headers: " + correlationId.getCorrelationId());

        requestContext.getHeaders().add(Filter.X_SHEEPY_CORRELATION_ID, correlationId.getCorrelationId());
    }
}
