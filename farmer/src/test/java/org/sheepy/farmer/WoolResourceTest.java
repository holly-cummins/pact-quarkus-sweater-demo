package org.sheepy.farmer;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class WoolResourceTest {

    @Test
    public void testWoolEndpointForWhiteWool() {
        Skein skein = given()
                .when().get("/wool/order/white")
                .then()
                .statusCode(200)
                .extract().as(Skein.class);

        assertEquals(skein.getColour(), "white");
    }

    @Test
    @Disabled
    public void testWoolEndpointForBlackWool() {
        Skein skein = given()
                .when().get("/wool/order/black")
                .then()
                .statusCode(200)
                .extract().as(Skein.class);

        assertEquals(skein.getColour(), "black");
    }

}