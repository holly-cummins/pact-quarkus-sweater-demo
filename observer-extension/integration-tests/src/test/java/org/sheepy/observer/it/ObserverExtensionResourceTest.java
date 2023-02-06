package org.sheepy.observer.it;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class ObserverExtensionResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/observer-extension")
                .then()
                .statusCode(200)
                .body(is("Hello observer-extension"));
    }
}
