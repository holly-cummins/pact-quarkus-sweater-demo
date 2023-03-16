package org.sheepy.observer;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.bson.types.ObjectId;
import org.jboss.resteasy.reactive.RestStreamElementType;

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntityBase;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

/**
 * Create self-diagramming architectures.
 */
@Path("/recorder")
public class RecorderResource {

    private static final List<Log> logs = new ArrayList<>();

    @Path("/component")
    @POST
    public Uni<ObjectId> addComponent(Component component) {
			return component.<Component>persistOrUpdate()
				.invoke(c -> System.out.println("\uD83C\uDFA5 [recorder] registered component " + c.getName()))
				.map(c -> c.id);
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
    public Uni<Void> addInteraction(Interaction interaction) {
        interaction.setTimestamp(System.currentTimeMillis());

				return interaction.<Interaction>persistOrUpdate()
	        .invoke(i -> System.out.println("\uD83C\uDFA5 [recorder] registering interaction " + i.getOwningComponent() + ":" + i.getMethodName()))
					.replaceWithVoid();
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
