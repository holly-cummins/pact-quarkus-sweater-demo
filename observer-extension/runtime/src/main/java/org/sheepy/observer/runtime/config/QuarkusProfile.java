package org.sheepy.observer.runtime.config;

import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;
import io.quarkus.runtime.annotations.StaticInitSafe;
import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "quarkus")
@StaticInitSafe // See https://github.com/quarkusio/quarkus/issues/18333
@ConfigRoot(phase = ConfigPhase.RUN_TIME)
public interface QuarkusProfile {


    /**
     * The profile we are running with
     */
    String profile();
}
