package org.sheepy.coldperson;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class SweaterResourceTest {

    @Test
    public void testSweaterEndpointReturnsAUniqueIncrementingOrderNumber() {
        SweaterOrder order = new SweaterOrder("white", 12);
        Sweater firstSweater = given()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post("/bff/order")
                .then()
                .statusCode(200)
                .extract().as(Sweater.class);
        Sweater secondSweater = given()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post("/bff/order")
                .then()
                .statusCode(200)
                .extract().as(Sweater.class);

        assertEquals(firstSweater.getOrderNumber() + 1, secondSweater.getOrderNumber());
    }

    @Test
    public void testSweaterEndpointForWhiteSweater() {
        SweaterOrder order = new SweaterOrder("white", 12);
        Sweater sweater = given()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post("/bff/order")
                .then()
                .statusCode(200)
                .extract().as(Sweater.class);

        assertEquals("white", sweater.getColour());
    }


}