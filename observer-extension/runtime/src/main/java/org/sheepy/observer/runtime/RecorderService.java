package org.sheepy.observer.runtime;

import org.sheepy.observer.runtime.config.QuarkusProfile;

import javax.enterprise.inject.Default;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import static org.sheepy.observer.runtime.LogHandler.LOG_PREFIX;

@Default
public class RecorderService {

    private final ObserverConfig config;
    private final QuarkusProfile profile;
    private final Client client;

    public RecorderService(ObserverConfig config, QuarkusProfile profile) {
        this.config = config;
        this.profile = profile;
        this.client = ClientBuilder.newClient();
    }

    private boolean isTestProfile() {
        return "test".equals(profile.profile());
    }

    public void log(String message) {
        if (!isTestProfile()) {
            try {
                client.target(config.baseURL).path("log")
                        .request(MediaType.TEXT_PLAIN).post(Entity.text(message));
                // Don't log anything back about the response or it ends up with too much circular logging
            } catch (Throwable e) {
                System.out.println(LOG_PREFIX + "Connection error: " + e);
            }
        }
    }

    public void registerComponent(String name) {
        if (!isTestProfile()) {
            Component component = new Component(name);

            try {
                client.target(config.baseURL).path("component")
                        .request(MediaType.APPLICATION_JSON)
                        .post(Entity.json(component));
            } catch (Throwable e) {
                System.out.println(LOG_PREFIX + "Connection error: " + e);
            }
        }
    }

    public void recordInteraction(Interaction interaction) {
        if (!isTestProfile()) {
            try {
                client.target(config.baseURL).path("interaction")
                        .request(MediaType.APPLICATION_JSON)
                        .post(Entity.json(interaction));

            } catch (Throwable e) {
                System.out.println(LOG_PREFIX + "Connection error: " + e);
            }
        }
    }
}

