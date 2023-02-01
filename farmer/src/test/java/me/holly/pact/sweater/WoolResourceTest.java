package me.holly.pact.sweater;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
public class WoolResourceTest {

    @Test
    public void testWoolEndpoint() {
        given()
                .when().get("/wool/order/white")
                .then()
                .statusCode(200)
                .body("colour", equalTo("white"));
    }

}