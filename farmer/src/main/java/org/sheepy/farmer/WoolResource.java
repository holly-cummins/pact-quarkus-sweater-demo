package org.sheepy.farmer;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/wool")
public class WoolResource {

    @Path("/order")
    @POST
    public Skein order(Order order) {
        Skein skein = new Skein();
        // For now, we have no ability to handle colour requests
        skein.setColour("white");
        return skein;
    }
}