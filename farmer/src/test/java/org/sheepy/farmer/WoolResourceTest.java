package org.sheepy.farmer;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

import io.restassured.http.ContentType;

@QuarkusTest
public class WoolResourceTest {

    // We should make these parameterised tests
    @Test
    public void testWoolEndpointForWhiteWool() {
        String colour = "white";
        Order order = new Order(colour, 2);
        Skein skein = given()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post("/wool/order")
                .then()
                .statusCode(200)
                .extract().as(Skein.class);

        assertEquals(skein.colour(), colour);
    }

    @Test
    public void testWoolEndpointForBlackWool() {
        String colour = "black";
        Order order = new Order(colour, 4);
        Skein skein = given()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post("/wool/order")
                .then()
                .statusCode(200)
                .extract().as(Skein.class);

        assertEquals(skein.colour(), colour);
    }

}