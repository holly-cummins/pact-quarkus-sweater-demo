package org.sheepy.knitter;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionHandler implements ExceptionMapper<NotFoundException> {

    @Context
    private HttpHeaders headers;

    public Response toResponse(NotFoundException ex) {
        return Response.status(418).entity(ex.getMessage()).build();

    }
}
