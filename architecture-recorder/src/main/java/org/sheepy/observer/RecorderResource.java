package org.sheepy.observer;

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntityBase;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.jboss.resteasy.reactive.RestStreamElementType;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Create self-diagramming architectures.
 */
@Path("/recorder")
public class RecorderResource {

    private static List<Interaction> interactions = new ArrayList<>();
    private static List<Log> logs = new ArrayList<>();

    @Path("/component")
    @POST
    public long addComponent(Component component) {

        // Components should be unique, so do a simple deduplication
        Component.find("name", component.getName()).count().subscribe().with(count -> {
            if (count.equals(0L)) {
                component.persistOrUpdate().subscribe().with(c -> System.out.println("\uD83C\uDFA5 [recorder] registered component " + ((Component) c).getName()));
            }
        });

        // This really should be the id we just created, but that's too much reactive programming for one day
        return 0;
    }

    @Path("/componentstream")
    @RestStreamElementType(MediaType.APPLICATION_JSON)
    @GET
    public Multi<Component> getComponentStream() {
        return Component.streamAll();
    }


    @Path("/components")
    @GET
    public Uni<List<ReactivePanacheMongoEntityBase>> getComponents() {

        return Component.listAll();
    }


    @Path("/interaction")
    @POST
    public void addInteraction(Interaction message) {
        System.out.println("\uD83C\uDFA5 [recorder] registering interaction " + message.getName());

        interactions.add(message);
    }

    @Path("/interactions")
    @GET
    public List<Interaction> getInteractions() {
        return interactions;
    }

    @Path("/log")
    @POST
    public void addLog(Log log) {
        System.out.println("\uD83C\uDFA5 [recorder] registering log " + log.getName());

        logs.add(log);
    }

    @Path("/logs")
    @GET
    public List<Log> getLogs() {
        return logs;
    }

    @Path("/clearall")
    @POST
    public Uni<Long> clearAll() {
        return Component.deleteAll();
        // TODO add other entities once we make them
    }
}
