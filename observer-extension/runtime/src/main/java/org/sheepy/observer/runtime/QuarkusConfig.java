package org.sheepy.observer.runtime;

import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;
import io.quarkus.runtime.annotations.StaticInitSafe;
import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "quarkus.application")
@StaticInitSafe // See https://github.com/quarkusio/quarkus/issues/18333
@ConfigRoot(phase = ConfigPhase.RUN_TIME)
public interface QuarkusConfig {


    /**
     * The name of the application.
     */
    String name();
}
