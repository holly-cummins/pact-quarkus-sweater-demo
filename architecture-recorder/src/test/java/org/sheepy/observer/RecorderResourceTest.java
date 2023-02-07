package org.sheepy.observer;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class RecorderResourceTest {

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
        Component[] components = given()
                .contentType(ContentType.JSON)
                .body(component)
                .when()
                .get("/recorder/components")
                .then()
                .statusCode(200)
                .extract().as(Component[].class);

        assertEquals(1, components.length);
        assertEquals(component.getName(), components[0].getName());
    }

    @Test
    public void testDuplicateComponentsAreStrippedOut() {
        Component component = new Component("important-part");

        // Record the same component twice
        given()
                .contentType(ContentType.JSON)
                .body(component)
                .when()
                .post("/recorder/component")
                .then()
                .statusCode(200);
        given()
                .contentType(ContentType.JSON)
                .body(component)
                .when()
                .post("/recorder/component")
                .then()
                .statusCode(200);
        Component[] components = given()
                .contentType(ContentType.JSON)
                .body(component)
                .when()
                .get("/recorder/components")
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
        Interaction[] interactions = given()
                .contentType(ContentType.JSON)
                .body(interaction)
                .when()
                .get("/recorder/interactions")
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
        Log[] logs = given()
                .contentType(ContentType.JSON)
                .body(log)
                .when()
                .get("/recorder/logs")
                .then()
                .statusCode(200)
                .extract().as(Log[].class);

        assertEquals(1, logs.length);
        assertEquals(log.getName(), logs[0].getName());
    }

}