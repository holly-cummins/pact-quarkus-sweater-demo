package org.sheepy.observer.runtime;

import javax.enterprise.inject.Default;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import static org.sheepy.observer.runtime.LogHandler.LOG_PREFIX;

@Default
public class RecorderService {

    private final ObserverConfig config;
    private final Client client;

    public RecorderService(ObserverConfig config) {
        this.config = config;
        this.client = ClientBuilder.newClient();
    }

    public void log(String message) {
        try {
            client.target(config.baseURL).path("log")
                    .request(MediaType.TEXT_PLAIN).post(Entity.text(message));
            // Don't log anything back about the response or it ends up with too much circular logging
        } catch (Throwable e) {
            System.out.println(LOG_PREFIX + "Connection error: " + e);
        }
    }

    public void registerComponent(String name) {
        Component component = new Component(name);

        try {
            client.target(config.baseURL).path("component")
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.json(component));
        } catch (Throwable e) {
            System.out.println(LOG_PREFIX + "Connection error: " + e);
        }
    }

    public void recordInteraction(Interaction interaction) {

        try {
            client.target(config.baseURL).path("interaction")
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.json(interaction));

        } catch (Throwable e) {
            System.out.println(LOG_PREFIX + "Connection error: " + e);
        }
    }

}

