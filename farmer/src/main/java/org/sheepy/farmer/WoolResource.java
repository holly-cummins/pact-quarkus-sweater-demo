package org.sheepy.farmer;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/wool")
public class WoolResource {

    @Path("/order")
    @POST
    public Skein shearSheep(Order order) {
        Sheep sheep;
        try {
            sheep = Sheep.valueOf(order.colour());
        } catch (IllegalArgumentException e) {
            sheep = Sheep.white;
        }
        return new Skein(sheep,
                order.orderNumber());
    }
}