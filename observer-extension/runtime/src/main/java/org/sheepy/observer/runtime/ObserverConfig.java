package org.sheepy.observer.runtime;

import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;

@ConfigRoot(phase = ConfigPhase.RUN_TIME)
public class ObserverConfig {

    /**
     * The recorder server's observability base URL
     */
    @ConfigItem(defaultValue = "http://localhost:8088/recorder/")
    public String baseURL;
}
