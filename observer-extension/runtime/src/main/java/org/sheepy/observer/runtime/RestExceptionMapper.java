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

    @Override
    public Response toResponse(Exception e) {
        recorder.recordEvent(e.getMessage());

        // We lose some detail about the exceptions here, especially for 404, but we will live with that
        return Response.serverError().build();

    }
}
