package me.holly.pact.sweater;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/wool")
public class WoolResource {

    @Path("/order/{colour}")
    @GET
    public Skein order(@PathParam("colour") Order order) {
        Skein skein = new Skein();
        skein.setColour("white");
        return skein;
    }
}