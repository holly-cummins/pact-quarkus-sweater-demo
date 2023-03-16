package org.sheepy.knitter;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;

import io.restassured.http.ContentType;

@QuarkusTest
public class SweaterResourceTest {

    @InjectMock
    @RestClient
    FarmerService mock;

    @BeforeEach
    public void setUp() {
        when(mock.getWool(any())).thenReturn(new Skein("brown"));
    }

    @Test
    public void testSweaterEndpointForWhiteSweater() {
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


}