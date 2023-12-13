package org.sheepy.observer.runtime;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@ObserverLog
@Interceptor
public class InteractionInterceptor {

    @ConfigProperty(name = "demo.interaction-latency", defaultValue = "336")
    int latency;
    private static final Pattern orderNumberPattern = Pattern.compile(".*\"orderNumber\":([0-9]*)" +
            ".*");

    private final RecorderService recorder;
    private final ObjectMapper mapper;
    private final QuarkusConfig appConfig;
    private final CorrelationId correlationId;

    public InteractionInterceptor(RecorderService recorder, QuarkusConfig appConfig,
                                  ObjectMapper mapper, CorrelationId correlationId) {
        this.recorder = recorder;
        this.appConfig = appConfig;
        this.mapper = mapper;
        this.correlationId = correlationId;
    }

    @AroundInvoke
    Object around(InvocationContext context) throws Exception {

        Method method = context.getMethod();

        // Be a bit sloppy about methods which take multiple arguments, since we think they are
        // unlikely
        String payload = "";
        for (Object o : context.getParameters()) {
            payload += mapper.writeValueAsString(o);
        }

        Interaction interaction = new Interaction();
        interaction.setMethodName(method.getName());
        interaction.setOwningComponent(appConfig.name());
        interaction.setPayload(payload);
        interaction.setType(Type.Request);

        // Quick and dirty hack
        // Ideally we should read this from a header, not the payload, and it should be generic
        Matcher orderNumberMatcher = orderNumberPattern.matcher(payload);
        if (orderNumberMatcher.find()) {
            String match = orderNumberMatcher.group(1);
            interaction.setCorrelationId(match);
            correlationId.setCorrelationId(Integer.parseInt(match));
        }

        // Add a delay so things animate more nicely on the screen
        Thread.sleep(latency);

        recorder.recordInteraction(interaction);

        return context.proceed();
    }

}


