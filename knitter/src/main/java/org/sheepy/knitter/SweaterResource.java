package org.sheepy.knitter;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/sweater")
public class SweaterResource {
    @Inject
    @RestClient
    FarmerService farmer;

    @Path("/order")
    @POST
    public Sweater knitSweater(SweaterOrder order) {
        WoolOrder woolOrder = new WoolOrder(order.getColour(), order.getOrderNumber());
        Skein skein = farmer.getWool(woolOrder);
        Sweater sweater = new Sweater(skein, order.getOrderNumber());
        return sweater;
    }
}