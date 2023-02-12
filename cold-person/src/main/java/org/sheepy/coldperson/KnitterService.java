package org.sheepy.coldperson;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/sweater")
@RegisterRestClient(configKey = "knitter-api")
@Consumes("application/json")
public interface KnitterService {

    @Path("/order")
    @POST
    Sweater getSweater(SweaterOrder order);
}
