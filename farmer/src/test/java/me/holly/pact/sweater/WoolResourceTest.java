package me.holly.pact.sweater;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class WoolResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/wool")
          .then()
             .statusCode(200)
             .body(is("Hello here is my sheep"));
    }

}