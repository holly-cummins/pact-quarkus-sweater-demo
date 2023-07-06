package org.sheepy.knitter;

import static au.com.dius.pact.consumer.dsl.LambdaDsl.newJsonBody;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import au.com.dius.pact.consumer.junit.MockServerConfig;
import au.com.dius.pact.core.model.PactSpecVersion;
import io.quarkus.test.common.QuarkusTestResource;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response.Status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.sheepy.knitter.pactworkaround.PactMockServerWorkaround;
import org.sheepy.knitter.pactworkaround.PactMockServer;
import org.sheepy.knitter.wiremock.WireMockQuarkusTestResource;

import io.quarkus.test.junit.QuarkusTest;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import io.restassured.http.ContentType;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "farmer", pactVersion = PactSpecVersion.V4)
@MockServerConfig(port = "0")
@QuarkusTestResource(WireMockQuarkusTestResource.class)
@QuarkusTest
// I could not use the extension in the test base because of order.
@ExtendWith(PactMockServerWorkaround.class)
public class SweaterResourceContractTest extends PactConsumerTestBase {

	@Pact(consumer = "knitter")
	public V4Pact buyingASweater(PactDslWithProvider builder) {
		var headers = Map.of(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);

		// Here we define our mock, which is also our expectations for the provider

		// This defines what the body of the request could look like; we are generic and say it can be anything that meets the schema
		var woolOrderBody = newJsonBody(body -> body.stringType("colour").numberType("orderNumber")).build();

		var woolBody = newJsonBody(body -> body.stringValue("colour", "white")).build();
 
		return builder.uponReceiving("post request").path("/wool/order").headers(headers).method(HttpMethod.POST).body(woolOrderBody).willRespondWith().status(Status.OK.getStatusCode()).headers(headers).body(woolBody).toPact(V4Pact.class);
	}

	@Pact(consumer = "knitter")
	public V4Pact buyingAPinkSweater(PactDslWithProvider builder) {
		var headers = Map.of(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);

		// Here we define our mock, which is also our expectations for the provider

		// This defines what the body of the request could look like; we are generic and say it can be anything that meets the schema
		var woolOrderBody = newJsonBody(body -> body.stringValue("colour", "pink").numberType("orderNumber")).build();

		var woolBody = newJsonBody(body -> body.stringValue("colour", "pink")).build();

		return builder.uponReceiving("post request for pink sweater").path("/wool/order").headers(headers).method(HttpMethod.POST).body(woolOrderBody).willRespondWith().status(200).headers(headers).body(woolBody).toPact(V4Pact.class);
	}

	@Test
	@PactTestFor(pactMethod = "buyingASweater")
	public void testSweaterEndpointForWhiteSweater(/*final MockServer mockServer*/final PactMockServer wrapper) {
        forwardToPactServer(wrapper);

		SweaterOrder order = new SweaterOrder("white", 12);
		Sweater sweater = given().contentType(ContentType.JSON).body(order).when().post("/sweater/order").then().statusCode(200).extract().as(Sweater.class);

		assertEquals("white", sweater.colour());
	}

	@Test
	@PactTestFor(pactMethod = "buyingAPinkSweater")
	public void testSweaterEndpointForPinkSweater(/*final MockServer mockServer*/final PactMockServer wrapper) {
        forwardToPactServer(wrapper);

		SweaterOrder order = new SweaterOrder("pink", 16);
		Sweater sweater = given().contentType(ContentType.JSON).body(order).when().post("/sweater/order").then().statusCode(200).extract().as(Sweater.class);

		assertEquals("pink", sweater.colour());
	}

}