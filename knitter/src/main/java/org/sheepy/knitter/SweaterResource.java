package org.sheepy.knitter;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/sweater")
public class SweaterResource {

    @Path("/order")
    @POST
    public Sweater order(SweaterOrder order) {
        // Temporary code; we should of course get the wool from an outside service
        Skein skein = new Skein(order.getColour());
        Sweater sweater = new Sweater(skein, order.getSize());
        return sweater;
    }
}