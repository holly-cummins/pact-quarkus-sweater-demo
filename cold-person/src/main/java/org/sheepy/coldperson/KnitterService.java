package org.sheepy.coldperson;

import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.sheepy.observer.runtime.ClientFilter;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/sweater")
@RegisterRestClient(configKey = "knitter-api")
@Consumes("application/json")
// This manual instrumentation should not be necessary, but I can't get the automatic version working
@RegisterProvider(ClientFilter.class)
public interface KnitterService {

    @Path("/order")
    @POST
    Sweater getSweater(SweaterOrder order);
}
