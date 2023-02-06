package org.sheepy.observer.runtime;

import io.quarkus.runtime.annotations.Recorder;

@Recorder
public class HelloRecorder {

    ObserverConfig config;
    QuarkusConfig appConfig;
    RecorderService recorder;

    public HelloRecorder(ObserverConfig config, QuarkusConfig appConfig) {
        this.recorder = new RecorderService(config);
        this.appConfig = appConfig;
    }


    public void registerComponent() {
        recorder.registerComponent(appConfig.name());
    }

}