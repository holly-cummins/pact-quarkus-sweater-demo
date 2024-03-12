package org.sheepy.knitter;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.any;
import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

import org.junit.jupiter.api.BeforeEach;
import org.sheepy.knitter.pactworkaround.PactMockServer;
import org.sheepy.knitter.wiremock.InjectWireMock;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

public class PactConsumerTestBase {

    @InjectWireMock
    protected WireMockServer wiremock;

    @BeforeEach
    void initWiremockBeforeEach() {
        wiremock.resetAll();
        configureFor(new WireMock(this.wiremock));
    }

    protected void forwardToPactServer(/*final MockServer mockServer*/ final PactMockServer wrapper) {
        wiremock.resetAll();
        stubFor(any(anyUrl()).atPriority(1).willReturn(aResponse().proxiedFrom(wrapper.getUrl())));
    }
}
