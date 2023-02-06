package org.sheepy.observer;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create self-diagramming architectures.
 */
@Path("/recorder")
public class RecorderResource {

    // Very lazy state handling - just put everything in memory
    // We should eventually switch to using panache
    private static Map<String, Component> components = new HashMap<>();
    private static List<Interaction> interactions = new ArrayList<>();
    private static List<Log> logs = new ArrayList<>();

    @Path("/component")
    @POST
    public void addComponent(Component component) {
        System.out.println("\uD83C\uDFA5 [recorder] registering component " + component.getName());
        // Components should be unique

        components.put(component.getName(), component);
    }

    @Path("/components")
    @GET
    public Collection<Component> getComponents() {
        return components.values();
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
}
