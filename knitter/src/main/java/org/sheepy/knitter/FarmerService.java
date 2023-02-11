package org.sheepy.knitter;

import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.sheepy.observer.runtime.ClientFilter;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/wool")
@RegisterRestClient(configKey = "farmer-api")
@Consumes("application/json")
// This manual instrumentation should not be necessary, but I can't get the automatic version working
@RegisterProvider(ClientFilter.class)
public interface FarmerService {

    @Path("/order")
    @POST
    Skein getWool(WoolOrder order);
}
