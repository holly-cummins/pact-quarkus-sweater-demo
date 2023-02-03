package org.sheepy.knitter;

import io.quarkus.test.Mock;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;

@Mock
@RestClient
@ApplicationScoped
public class MockFarmerService implements FarmerService {

    @Override
    public Skein getWool(WoolOrder order) {
        // Beware! This bakes an assumption that the farmer has all possible colours available
        return new Skein(order.getColour());
    }
}

