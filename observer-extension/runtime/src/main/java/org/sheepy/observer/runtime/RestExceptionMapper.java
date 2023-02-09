package org.sheepy.observer.runtime;


import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class RestExceptionMapper
        implements ExceptionMapper<Exception> {
    @Context
    private RecorderService recorder;

    @Context
    private QuarkusConfig appConfig;

    @Override
    public Response toResponse(Exception e) {
        Interaction interaction = new Interaction();
        interaction.setPayload("{\"exception\": \"" + e.getMessage() + "\"}");
        interaction.setOwningComponent(appConfig.name());
        // For now, consider the exceptions to be requests
        interaction.setType(Type.Request);
        recorder.recordInteraction(interaction);

        // We lose some detail about the exceptions here, especially for 404, but we will live with that
        return Response.serverError().build();

    }
}
