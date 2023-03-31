package org.sheepy.observer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.quarkus.mongodb.ChangeStreamOptions;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntityBase;
import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.quarkus.mongodb.reactive.ReactiveMongoDatabase;

import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.changestream.ChangeStreamDocument;
import com.mongodb.client.model.changestream.FullDocument;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

/**
 * Create self-diagramming architectures.
 */
@Path("/recorder")
public class RecorderResource {

	private static final List<Log> logs = new ArrayList<>();

	@Inject
	ReactiveMongoClient mongoClient;

	@ConfigProperty(name = "quarkus.mongodb.database", defaultValue = "architecture")
	String databaseName;

	@Path("/component")
	@POST
	public Uni<ObjectId> addComponent(Component component) {
		return component.<Component>persistOrUpdate().invoke(c -> System.out.println("\uD83C\uDFA5 [recorder] registered component " + c.getName())).map(c -> c.id);
	}

	@Path("/componentstream")
	@Produces(MediaType.SERVER_SENT_EVENTS)
	@Blocking
	@GET
	public Multi<Component> streamComponents() {
		// We can't just use panache streamAll, we need to do a watch: Mongo will execute the query and give you the stream.
		ReactiveMongoDatabase database = mongoClient.getDatabase(databaseName);
		ReactiveMongoCollection<Component> dataCollection = database.getCollection(Component.class.getSimpleName(), Component.class);
		ChangeStreamOptions options = new ChangeStreamOptions().fullDocument(FullDocument.UPDATE_LOOKUP);
		List<Bson> pipeline = Collections.singletonList(Aggregates.match(Filters.and(Filters.eq("operationType", "insert"))));
		return dataCollection.watch(pipeline, Component.class, options).map(ChangeStreamDocument::getFullDocument);
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

		return interaction.<Interaction>persistOrUpdate().invoke(i -> System.out.println("\uD83C\uDFA5 [recorder] registering interaction " + i.getOwningComponent() + ":" + i.getMethodName())).replaceWithVoid();
	}

	@Path("/interactionstream")
	@Produces(MediaType.SERVER_SENT_EVENTS)
	@Blocking
	@GET
	public Multi<Interaction> streamInteractions() {
		// We can't just use panache streamAll, we need to do a watch: Mongo will execute the query and give you the stream.
		// "Except if you use a mongo watch, it won't give you the update, just the initial query result (as a stream)"
		ReactiveMongoDatabase database = mongoClient.getDatabase(databaseName);
		ReactiveMongoCollection<Interaction> dataCollection = database.getCollection(Interaction.class.getSimpleName(), Interaction.class);
		ChangeStreamOptions options = new ChangeStreamOptions().fullDocument(FullDocument.UPDATE_LOOKUP);
		List<Bson> pipeline = Collections.singletonList(Aggregates.match(Filters.and(Filters.eq("operationType", "insert"))));
		return dataCollection.watch(pipeline, Interaction.class, options).map(ChangeStreamDocument::getFullDocument);
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
