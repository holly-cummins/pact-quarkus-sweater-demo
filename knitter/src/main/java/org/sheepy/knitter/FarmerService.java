package org.sheepy.knitter;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/wool")
@RegisterRestClient(configKey = "farmer-api")
@Consumes("application/json")
public interface FarmerService {

    @Path("/order")
    @POST
    Skein getWool(WoolOrder order);
}
