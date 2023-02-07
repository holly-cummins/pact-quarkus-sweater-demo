package org.sheepy.observer.runtime;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.lang.reflect.Method;


@ObserverLog
@Interceptor
public class LogInterceptor {
    private final RecorderService recorder;
    QuarkusConfig appConfig;

    public LogInterceptor(RecorderService recorder, QuarkusConfig appConfig) {
        this.recorder = recorder;
        this.appConfig = appConfig;
    }

    @AroundInvoke
    Object around(InvocationContext context) throws Exception {

        Method method = context.getMethod();
        Interaction interaction = new Interaction();
        interaction.setMethodName(method.getName());
        interaction.setOwningComponent(appConfig.name());

        recorder.recordInteraction(interaction);

        return context.proceed();
    }

}


