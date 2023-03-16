package org.sheepy.coldperson;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/sweater")
@RegisterRestClient(configKey = "knitter-api")
@Consumes("application/json")
public interface KnitterService {

    @Path("/order")
    @POST
    Sweater getSweater(SweaterOrder order);
}
