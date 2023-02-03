package org.sheepy.coldperson;

import io.quarkus.test.Mock;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;

@Mock
@RestClient
@ApplicationScoped
public class MockKnitterService implements KnitterService {

    @Override
    public Sweater getSweater(SweaterOrder order) {
        Sweater sweater = new Sweater();
        sweater.setColour(order.getColour());
        return sweater;
    }
}

