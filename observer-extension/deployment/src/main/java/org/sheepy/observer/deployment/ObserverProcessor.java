package org.sheepy.observer.deployment;

import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.arc.deployment.AnnotationsTransformerBuildItem;
import io.quarkus.arc.deployment.BeanContainerBuildItem;
import io.quarkus.arc.processor.AnnotationsTransformer;
import io.quarkus.deployment.Capabilities;
import io.quarkus.deployment.Capability;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.LogHandlerBuildItem;
import io.quarkus.resteasy.reactive.spi.ContainerResponseFilterBuildItem;
import io.quarkus.resteasy.reactive.spi.ExceptionMapperBuildItem;
import org.jboss.jandex.DotName;
import org.sheepy.observer.runtime.ClientFilter;
import org.sheepy.observer.runtime.ComponentRecorder;
import org.sheepy.observer.runtime.CorrelationId;
import org.sheepy.observer.runtime.Filter;
import org.sheepy.observer.runtime.InteractionInterceptor;
import org.sheepy.observer.runtime.LogHandlerMaker;
import org.sheepy.observer.runtime.ObserverLog;
import org.sheepy.observer.runtime.RecorderService;
import org.sheepy.observer.runtime.RestExceptionMapper;

import javax.ws.rs.Priorities;

import static io.quarkus.deployment.annotations.ExecutionTime.RUNTIME_INIT;

class ObserverProcessor {

    private static final String FEATURE = "observer-extension";
    private static final DotName JAX_RS_GET = DotName.createSimple("javax.ws.rs.GET");
    private static final DotName JAX_RS_POST = DotName.createSimple("javax.ws.rs.POST");
    private static final DotName CLIENT_FILTER = DotName.createSimple(ClientFilter.class.getName());
    private static final DotName REGISTER_REST_CLIENT = DotName.createSimple("org.eclipse.microprofile.rest.client.inject.RestClient");
    private static final DotName OIDC_CLIENT_REQUEST_REACTIVE_FILTER = DotName.createSimple("org.eclipse.microprofile.rest.client.inject.RegisterRestClient");

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @Record(RUNTIME_INIT)
    @BuildStep
    public void helloBuildStep(ComponentRecorder recorder) {
        recorder.registerComponent();
    }

    @Record(RUNTIME_INIT)
    @BuildStep
    LogHandlerBuildItem addLogHandler(final LogHandlerMaker maker, BeanContainerBuildItem beanContainer) {
        return new LogHandlerBuildItem(maker.create(beanContainer.getValue()));
    }

    /**
     * Makes the interceptor as a bean so we can access it.
     */
    @BuildStep
    void beans(BuildProducer<AdditionalBeanBuildItem> producer) {
        producer.produce(AdditionalBeanBuildItem.unremovableOf(InteractionInterceptor.class));
        producer.produce(AdditionalBeanBuildItem.unremovableOf(Filter.class));
        producer.produce(AdditionalBeanBuildItem.unremovableOf(ClientFilter.class));
        producer.produce(AdditionalBeanBuildItem.unremovableOf(RecorderService.class));
        producer.produce(AdditionalBeanBuildItem.unremovableOf(CorrelationId.class));
    }

    @BuildStep
    AnnotationsTransformerBuildItem transform() {
        return new AnnotationsTransformerBuildItem(new AnnotationsTransformer() {

            public boolean appliesTo(org.jboss.jandex.AnnotationTarget.Kind kind) {
                return kind == org.jboss.jandex.AnnotationTarget.Kind.METHOD;
            }

            public void transform(TransformationContext context) {
                if (context.getTarget().asMethod().hasAnnotation(JAX_RS_GET)) {
                    context.transform().add(ObserverLog.class).done();

                }
                if (context.getTarget().asMethod().hasAnnotation(JAX_RS_POST)) {
                    context.transform().add(ObserverLog.class).done();
                }

            }
        });
    }

    @BuildStep
    void registerResteasyReactiveProvider(
            Capabilities capabilities,
            BuildProducer<ContainerResponseFilterBuildItem> containerRequestFilterBuildItemBuildProducer) {

        boolean isResteasyReactiveAvailable = capabilities.isPresent(Capability.RESTEASY_REACTIVE);

        if (!isResteasyReactiveAvailable) {
            System.out.println("Rest Easy Reactive is not present, so no instrumentation will be added.");
            // if RestEasy is not available then no need to continue
            return;
        }


        containerRequestFilterBuildItemBuildProducer
                .produce(new ContainerResponseFilterBuildItem.Builder(Filter.class.getName())
                        .build());
    }

    @BuildStep
    ExceptionMapperBuildItem exceptionMappers() {
        return new ExceptionMapperBuildItem(RestExceptionMapper.class.getName(),
                Exception.class.getName(), Priorities.USER + 100, true);
    }

}
