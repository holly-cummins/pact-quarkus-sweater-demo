package org.sheepy.knitter;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/wool")
@RegisterRestClient(configKey = "farmer-api")
@Consumes("application/json")
public interface FarmerService {

    @Path("/order")
    @POST
    Skein getWool(WoolOrder order);
}
