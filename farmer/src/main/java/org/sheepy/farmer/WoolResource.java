package org.sheepy.farmer;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/wool")
public class WoolResource {

    @Path("/order")
    @POST
    public Skein shearSheep(Order order) {
        Sheep sheep;
        try {
            sheep = Sheep.valueOf(order.getColour().toLowerCase());
        } catch (IllegalArgumentException e) {
            sheep = Sheep.white;
        }
        Skein skein = new Skein(sheep, order.getOrderNumber());
        return skein;
    }
}