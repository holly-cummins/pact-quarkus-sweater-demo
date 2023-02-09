package org.sheepy.observer.runtime;

import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;
import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "quarkus.application")
@ConfigRoot(phase = ConfigPhase.RUN_TIME)
public interface QuarkusConfig {


    /**
     * The name of the application.
     */
    String name();
}
