package org.sheepy.knitter.pactworkaround;

// Cannot be a record. Otherwise, it fails again with xstream
public class PactMockServer {

    private final String url;
    private final int port;

    public PactMockServer(String url, int port) {
        this.url = url;
        this.port = port;
    }

    public String getUrl() {
        return url;
    }

    // Not used, but maybe somebody needs that.
    public int getPort() {
        return port;
    }
}
