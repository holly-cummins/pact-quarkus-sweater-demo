package org.sheepy.farmer;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/wool")
public class WoolResource {

    @Path("/order")
    @POST
    public Skein order(Order order) {
        Sheep sheep = Sheep.valueOf(order.getColour().toLowerCase());
        Skein skein = new Skein(sheep);
        return skein;
    }
}