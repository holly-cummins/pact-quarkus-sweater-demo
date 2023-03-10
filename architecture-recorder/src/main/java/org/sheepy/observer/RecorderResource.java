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

    private static final List<Log> logs = new ArrayList<>();

    @Path("/component")
    @POST
    public long addComponent(Component component) {
        component.persistOrUpdate().subscribe().with(c -> System.out.println("\uD83C\uDFA5 [recorder] registered component " + ((Component) c).getName()));
        // This really should be the id we just created, but that's too much reactive programming for one day
        return 0;
    }

    @Path("/componentstream")
    @RestStreamElementType(MediaType.APPLICATION_JSON)
    @GET
    public Multi<Component> streamComponents() {
        return Component.streamAll();
    }


    @Path("/components")
    @GET
    public Uni<List<ReactivePanacheMongoEntityBase>> getComponents() {

        return Component.listAll();
    }


    @Path("/interaction")
    @POST
    public void addInteraction(Interaction interaction) {
        interaction.setTimestamp(System.currentTimeMillis());
        interaction.persistOrUpdate().subscribe().with(i -> System.out.println("\uD83C\uDFA5 [recorder] registering interaction " + ((Interaction) i).getOwningComponent() + ":" + ((Interaction) i).getMethodName()));
    }

    @Path("/interactionstream")
    @RestStreamElementType(MediaType.APPLICATION_JSON)
    @GET
    public Multi<Component> streamInteractions() {
        return Interaction.streamAll();
    }


    @Path("/interactions")
    @GET
    public Uni<List<Interaction>> getInteractions() {
        return Interaction.listAll();
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
        return Component.deleteAll().chain(() -> Interaction.deleteAll());
    }

    @Path("/clearinteractions")
    @POST
    public Uni<Long> clearInteractions() {
        return Interaction.deleteAll();
    }
}
