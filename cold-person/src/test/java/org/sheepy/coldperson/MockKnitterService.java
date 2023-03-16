package org.sheepy.coldperson;

import jakarta.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.quarkus.test.Mock;

@Mock
@RestClient
@ApplicationScoped
public class MockKnitterService implements KnitterService {

    @Override
    public Sweater getSweater(SweaterOrder order) {
			return new Sweater(order.colour(), order.orderNumber());
    }
}

