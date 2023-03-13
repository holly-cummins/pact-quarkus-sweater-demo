package org.sheepy.knitter;

import static au.com.dius.pact.consumer.dsl.LambdaDsl.newJsonBody;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.quarkus.test.junit.QuarkusTest;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import io.restassured.http.ContentType;

@QuarkusTest
@PactTestFor(providerName = "farmer", port = "8096")
@ExtendWith(PactConsumerTestExt.class)
public class SweaterResourceTest {
    @Pact(consumer = "knitter")
    public V4Pact buyingASweater(PactDslWithProvider builder) {
        var headers = Map.of(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);

        var woolOrderBody = newJsonBody(body ->
          body
            .stringValue("colour", "white")
            .numberType("orderNumber")
        ).build();

        var woolBody = newJsonBody(body -> body.stringValue("colour", "white")).build();

        return builder
          .uponReceiving("post request")
            .path("/wool/order")
            .headers(headers)
            .method(HttpMethod.POST)
            .body(woolOrderBody)
          .willRespondWith()
            .status(Status.OK.getStatusCode())
            .headers(headers)
            .body(woolBody)
          .toPact(V4Pact.class);
    }

    @Pact(consumer = "knitter")
    public V4Pact buyingAPinkSweater(PactDslWithProvider builder) {
        var headers = Map.of(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);

        var woolOrderBody = newJsonBody(body ->
          body
            .stringValue("colour", "pink")
            .numberType("orderNumber")
        ).build();

        var woolBody = newJsonBody(body -> body.stringValue("colour", "pink")).build();

        return builder
          .uponReceiving("post request for pink sweater")
            .path("/wool/order")
            .headers(headers)
            .method(HttpMethod.POST)
            .body(woolOrderBody)
          .willRespondWith()
            .status(418)
            .headers(headers)
            .body(woolBody)
          .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "buyingASweater")
    public void testSweaterEndpointForWhiteSweater() {
        SweaterOrder order = new SweaterOrder("white", 12);
        Sweater sweater = given()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post("/sweater/order")
                .then()
                .statusCode(200)
                .extract().as(Sweater.class);

        assertEquals("white", sweater.getColour());
    }

    @Test
    @PactTestFor(pactMethod = "buyingAPinkSweater")
    public void testSweaterEndpointForPinkSweater() {
        SweaterOrder order = new SweaterOrder("pink", 10);
        given()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post("/sweater/order")
                .then()
                .statusCode(418);
    }


}