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
        Skein skein = farmer.getWool(new WoolOrder(order.getColour(), order.getOrderNumber()));
        Sweater sweater = new Sweater(skein, order.getOrderNumber());
        return sweater;
    }
}