package org.sheepy.coldperson;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 * This is a BFF for the cold person frontend.
 * Here, BFF could be "backend for frontend" or "best friend forever."
 */
@Path("/bff")
public class SweaterResource {
    @Inject
    @RestClient
    KnitterService knitter;

    @Path("/order")
    @POST
    public Sweater order(SweaterOrder order) {
        Sweater sweater = knitter.getSweater(order);
        return sweater;
    }
}