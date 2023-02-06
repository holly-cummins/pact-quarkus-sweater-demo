package org.sheepy.coldperson;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * This is a BFF for the cold person frontend.
 * Here, BFF could be "backend for frontend" or "best friend forever."
 */
@Path("/bff")
public class SweaterResource {
    @Inject
    @RestClient
    KnitterService knitter;

    @Inject
    OrderNumber orderNumber;

    @Path("/order")
    @POST
    public Sweater order(SweaterOrder order) {
        Sweater sweater = knitter.getSweater(order);
        sweater.setOrderNumber(orderNumber.get());
        return sweater;
    }
}