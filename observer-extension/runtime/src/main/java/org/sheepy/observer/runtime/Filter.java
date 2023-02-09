package org.sheepy.observer.runtime;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.sheepy.observer.runtime.config.QuarkusProfile;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class Filter implements ContainerResponseFilter {

    @Inject
    ObjectMapper mapper;

    @Inject
    QuarkusConfig appConfig;

    @Inject
    QuarkusProfile quarkusProfile;

    public Filter() {
    }

    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) throws IOException {
        Object o = responseContext.getEntity();
        String payload = mapper.writeValueAsString(o);

        RecorderService recorder = new RecorderService(null, quarkusProfile);

        Interaction interaction = new Interaction();
        interaction.setMethodName("[response]"); // It's a bit hard finding out the method name or URL, so pretend we don't care
        interaction.setType(Type.Response);
        interaction.setOwningComponent(appConfig.name());
        interaction.setPayload(payload);

        recorder.recordInteraction(interaction);
    }
}

