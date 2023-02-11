package org.sheepy.observer.runtime;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class Filter implements ContainerResponseFilter {

    public static final String X_SHEEPY_CORRELATION_ID = "X-sheepy-correlation-id";

    @Inject
    ObjectMapper mapper;

    @Inject
    Instance<QuarkusConfig> appConfig;
    // Why an instance? See https://github.com/quarkusio/quarkus/issues/18333 and https://stackoverflow.com/questions/68769397/jar-rs-filter-injection-of-a-cdi-singleton-that-reference-a-configmapping-objec
    // ... but also come back and try with @StaticInitSafe once https://github.com/quarkusio/quarkus/pull/30964 is in a release

    @Inject
    Instance<RecorderService> service;

    public Filter() {
    }

    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) throws IOException {
        Object o = responseContext.getEntity();
        String payload = mapper.writeValueAsString(o);

        RecorderService recorder = service.get();

        Interaction interaction = new Interaction();
        interaction.setMethodName("[response]"); // It's a bit hard finding out the method name or URL, so pretend we don't care
        interaction.setType(Type.Response);
        interaction.setOwningComponent(appConfig.get().name());
        interaction.setPayload(payload);

        String correlationId = requestContext.getHeaderString(X_SHEEPY_CORRELATION_ID);
        if (correlationId != null) {
            interaction.setCorrelationId(correlationId);
            responseContext.getHeaders().add(X_SHEEPY_CORRELATION_ID, correlationId);
        }


        recorder.recordInteraction(interaction);
    }
}

