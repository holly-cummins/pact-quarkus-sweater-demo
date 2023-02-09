package org.sheepy.observer.runtime;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.lang.reflect.Method;


@ObserverLog
@Interceptor
public class InteractionInterceptor {
    private final RecorderService recorder;
    private final ObjectMapper mapper;
    private final QuarkusConfig appConfig;

    public InteractionInterceptor(RecorderService recorder, QuarkusConfig appConfig, ObjectMapper mapper) {
        this.recorder = recorder;
        this.appConfig = appConfig;
        this.mapper = mapper;
    }

    @AroundInvoke
    Object around(InvocationContext context) throws Exception {

        Method method = context.getMethod();

        // Be a bit sloppy about methods which take multiple arguments, since we think they are unlikely
        String payload = "";
        for (Object o : context.getParameters()) {
            payload += mapper.writeValueAsString(o);
        }
        Interaction interaction = new Interaction();
        interaction.setMethodName(method.getName());
        interaction.setOwningComponent(appConfig.name());
        interaction.setPayload(payload);
        interaction.setType(Type.Request);

        recorder.recordInteraction(interaction);

        return context.proceed();
    }

}


