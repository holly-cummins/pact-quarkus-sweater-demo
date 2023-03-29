package org.sheepy.observer;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

import io.restassured.http.ContentType;

@QuarkusTest
public class RecorderResourceTest {
    private static final long CURRENT_TIMESTAMP = System.currentTimeMillis();

    @BeforeEach
    public void setUp() {
        given()
                .when()
                .post("/recorder/clearall")
                .then()
                .statusCode(200);
    }

    @Test
    public void testAddingAComponentIncludesItInComponents() {
        Component component = new Component("important-part");

        // First record a component
        given()
                .contentType(ContentType.JSON)
                .body(component)
                .when()
                .post("/recorder/component")
                .then()
                .statusCode(200);
        Component[] components = get("/recorder/components")
                .then()
                .statusCode(200)
                .extract().as(Component[].class);

        assertEquals(1, components.length);
        assertEquals(component.getName(), components[0].getName());
    }

    @Test
    public void testAddingAInteractionIncludesItInInteractions() {
        String payload = "{\"thing\": \"value\"}";
        Interaction interaction = new Interaction();
        interaction.setMethodName("doTheThing");
        interaction.setOwningComponent("widgets");

        // First record a interaction
        given()
                .contentType(ContentType.JSON)
                .body(interaction)
                .when()
                .post("/recorder/interaction")
                .then()
                .statusCode(204);
        Interaction[] interactions = get("/recorder/interactions")
                .then()
                .statusCode(200)
                .extract().as(Interaction[].class);

        assertEquals(1, interactions.length);
        assertEquals(interaction.getMethodName(), interactions[0].getMethodName());
    }


    @Test
    public void testAddingALogIncludesItInLogs() {
        Log log = new Log("something happened");

        // First record a log
        given()
                .contentType(ContentType.JSON)
                .body(log)
                .when()
                .post("/recorder/log")
                .then()
                .statusCode(204);
        Log[] logs = get("/recorder/logs")
                .then()
                .statusCode(200)
                .extract().as(Log[].class);

        assertEquals(1, logs.length);
        assertEquals(log.getName(), logs[0].getName());
    }

    @Test
    public void testStreamingComponents() {
        var componentNames = List.of("component-a", "component-b");
        var components = componentNames.stream()
          .map(Component::new)
          .toList();

        components.forEach(component -> {
              var id = given()
                .contentType(ContentType.JSON)
                .body(component)
                .when()
                .post("/recorder/component")
                .then()
                .statusCode(200)
                .extract().as(ObjectId.class);

              component.id = id;
          }
        );

        var componentJson = components.stream()
          .map(c -> String.format("data:{\"id\":\"%s\",\"name\":\"%s\"}", c.id, c.getName()))
          .collect(Collectors.joining("\n\n", "", "\n\n"));

        get("/recorder/componentstream").then()
          .statusCode(200)
          .contentType("text/event-stream")
          .body(is(componentJson));
    }

    @Test
    public void testStreamingInteractions() {
        var interactions = List.of(
          createInteraction(1, Type.Request),
          createInteraction(2, Type.Response)
        );

        interactions.forEach(interaction -> {
          given()
            .contentType(ContentType.JSON)
            .body(interaction)
            .when()
            .post("/recorder/interaction")
            .then()
            .statusCode(204);
          }
        );

        var sseString = get("/recorder/interactionstream").then()
          .statusCode(200)
          .contentType("text/event-stream")
          .extract()
          .body().asString();

        interactions.stream()
          .flatMap(i ->
            Stream.of(
              String.format("\"methodName\":\"%s\"", i.getMethodName()),
              String.format("\"owningComponent\":\"%s\"", i.getOwningComponent()),
              String.format("\"payload\":\"%s\"", i.getPayload().replace("\"", "\\\"")),
              String.format("\"correlationId\":\"%s\"", i.getCorrelationId()),
              String.format("\"type\":\"%s\"", i.getType())
            )
          )
          .forEach(interactionPart -> assertThat(sseString).contains(interactionPart));
    }

    private static Interaction createInteraction(int interactionNumber, Type type) {
        var interaction = new Interaction();
        interaction.setMethodName("m" + interactionNumber);
        interaction.setOwningComponent("o" + interactionNumber);
        interaction.setPayload(String.format("{\"thing%d\":\"value%d\"}", interactionNumber, interactionNumber));
        interaction.setCorrelationId("c" + interactionNumber);
        interaction.setTimestamp(CURRENT_TIMESTAMP);
        interaction.setType(type);

        return interaction;
    }
}