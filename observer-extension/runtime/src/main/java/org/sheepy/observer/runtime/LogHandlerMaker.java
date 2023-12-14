package org.sheepy.observer.runtime;

import io.quarkus.arc.runtime.BeanContainer;
import io.quarkus.runtime.RuntimeValue;
import io.quarkus.runtime.annotations.Recorder;

import java.util.Optional;
import java.util.logging.Handler;

@Recorder
public class LogHandlerMaker {

    public RuntimeValue<Optional<Handler>> create(BeanContainer beanContainer) {
        RecorderService recorder = beanContainer.beanInstance(RecorderService.class);
        Handler handler = new LogHandler(recorder);
        return new RuntimeValue<>(Optional.of(handler));

    }
}

