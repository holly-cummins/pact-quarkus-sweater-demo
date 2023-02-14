package org.sheepy.coldperson;

import io.quarkiverse.quinoa.testing.QuinoaTestProfiles;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import org.junit.jupiter.api.Test;

@QuarkusTest
@TestProfile(QuinoaTestProfiles.EnableAndRunTests.class)
public class WebUITest {
    @Test
    public void runTest() {
        // you don't need anything here, it will run your package.json "test"
    }
}