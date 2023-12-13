package org.sheepy.knitter;

import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/sweater")
public class SweaterResource {
    @Inject
    @RestClient
    FarmerService farmer;

    @Path("/order")
    @POST
    public Sweater knitSweater(SweaterOrder order) {
        WoolOrder woolOrder = new WoolOrder(order.colour(),
                order.orderNumber());
        try {
            Skein skein = farmer.getWool(woolOrder);
            Sweater sweater = new Sweater(skein,
                    order.orderNumber());
            return sweater;
        } catch (Exception e) {
            throw new NotFoundException("Order failed for sweater colour " + order.colour());
        }
    }
}