package org.sheepy.knitter;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/sweater")
public class SweaterResource {
    @Inject
    @RestClient
    FarmerService farmer;

    @Path("/order")
    @POST
    public Sweater knitSweater(SweaterOrder order) {
        WoolOrder woolOrder = new WoolOrder(order.colour(), order.orderNumber());
        try {
            Skein skein = farmer.getWool(woolOrder);
            Sweater sweater = new Sweater(skein, order.orderNumber());
            return sweater;
        } catch (Exception e) {
            throw new NotFoundException(order.colour());
        }
    }
}