package org.sheepy.observer.runtime;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.lang.reflect.Method;


@ObserverLog
@Interceptor
public class LogInterceptor {
    private final RecorderService recorder;

    public LogInterceptor(RecorderService recorder) {
        this.recorder = recorder;
    }

    @AroundInvoke
    Object around(InvocationContext context) throws Exception {

        Method method = context.getMethod();
        recorder.recordInteraction(method.getName());

        return context.proceed();
    }

}


