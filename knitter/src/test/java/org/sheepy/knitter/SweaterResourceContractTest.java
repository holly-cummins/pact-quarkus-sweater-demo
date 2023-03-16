package org.sheepy.knitter;

import static au.com.dius.pact.consumer.dsl.LambdaDsl.newJsonBody;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response.Status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.quarkus.test.junit.QuarkusTest;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import io.restassured.http.ContentType;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "farmer", port = "8096")
@QuarkusTest
public class SweaterResourceContractTest {

    @Pact(consumer = "knitter")
    public V4Pact buyingASweater(PactDslWithProvider builder) {
        var headers = Map.of(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);

        // Here we define our mock, which is also our expectations for the provider

        // This defines what the body of the request could look like; we are generic and say it can be anything that meets the schema
        var woolOrderBody = newJsonBody(body ->
          body
            .stringType("colour")
            .numberType("orderNumber")
        ).build();

        var woolBody = newJsonBody(body -> body.stringValue("colour", "brown")).build();

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

        // Here we define our mock, which is also our expectations for the provider

        // This defines what the body of the request could look like; we are generic and say it can be anything that meets the schema
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
                    .status(200)
                    .headers(headers)
                    .body(woolBody)
                .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "buyingASweater")
    public void testSweaterEndpointForBrownSweater() {
        SweaterOrder order = new SweaterOrder("brown", 12);
        Sweater sweater = given()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post("/sweater/order")
                .then()
                .statusCode(200)
                .extract().as(Sweater.class);

        assertEquals("brown", sweater.colour());
    }

    @Test
    @PactTestFor(pactMethod = "buyingAPinkSweater")
    public void testSweaterEndpointForPinkSweater() {
        SweaterOrder order = new SweaterOrder("pink", 16);
        Sweater sweater = given()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post("/sweater/order")
                .then()
                .statusCode(200)
                .extract().as(Sweater.class);

        assertEquals("pink", sweater.colour());
    }

}