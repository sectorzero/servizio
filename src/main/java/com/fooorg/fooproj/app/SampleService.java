package com.fooorg.fooproj.app;

import com.fooorg.fooproj.configuration.FooModule;
import com.fooorg.fooproj.configuration.SampleServiceConfiguration;
import com.fooorg.fooproj.configuration.SampleServiceConfigurationModule;
import com.fooorg.fooproj.configuration.SampleServiceModule;
import com.fooorg.fooproj.core.FooDataAccess;
import com.fooorg.fooproj.resources.ComplexResource;
import com.fooorg.fooproj.resources.FooResource;
import com.fooorg.fooproj.resources.HolaResource;

import com.jasonclawson.dropwizardry.guice.AbstractDropwizardModule;
import com.jasonclawson.dropwizardry.guice.GuiceApplication;
import com.jasonclawson.dropwizardry.guice.RuntimeBundle;
import com.jasonclawson.dropwizardry.guice.support.GuiceSupport;

import io.federecio.dropwizard.swagger.SwaggerDropwizard;

import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

import com.google.inject.Inject;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.util.ContextInitializer;
import org.slf4j.LoggerFactory;

import java.util.stream.IntStream;

public class SampleService extends GuiceApplication<SampleServiceConfiguration> {

    public static void main(String[] args) throws Exception {
        new SampleService().run(args);
    }

    @Override
    public String getName() {
        return "Hola Sample Service";
    }

    @Override
    public void initialize(Bootstrap<SampleServiceConfiguration> bootstrap) {
    }

    @Override
    public void configure(
            final SampleServiceConfiguration configuration,
            GuiceSupport.Builder<SampleServiceConfiguration> guiceBuilder) {
        // Logging Setup
        try {
            // Ref : http://stackoverflow.com/questions/27356918/drop-wizard-request-response-logging
            resetLoggerToDefault();
        } catch(Exception e) {
            System.exit(1);
        }

        // Guice Modules
        guiceBuilder.addModule(new SampleServiceConfigurationModule());
        guiceBuilder.addModule(new SampleServiceModule());
        guiceBuilder.addModule(new FooModule());
        guiceBuilder.addModule(new AbstractDropwizardModule() {
            @Override
            protected void configureModule() {
                // Swagger
                addBundle(ViewBundle.class);
                addBundle(SwaggerBundle.class);

                // Resources
                //registerApiResourcesViaGuice(jersey());
                jersey().register(HolaResource.class);
                jersey().register(FooResource.class);
                jersey().register(ComplexResource.class);

                // Test
                addBundle(FooDataBundle.class);
            }
        });
    }

    private static class SwaggerBundle extends RuntimeBundle {
        final Configuration configuration;
        final SwaggerDropwizard swagger;

        @Inject
        public SwaggerBundle(Configuration configuration, SwaggerDropwizard swagger) {
            this.configuration = configuration;
            this.swagger = swagger;
        }

        @Override
        public void run(Environment environment) {
            try {
                swagger.onRun(configuration, environment);
            } catch (Exception e) {
                System.exit(1);
            }
        }
    }

    private static class FooDataBundle extends RuntimeBundle {

        final FooDataAccess fda;

        @Inject
        public FooDataBundle(FooDataAccess fda) {
            this.fda = fda;
        }

        @Override
        public void run(Environment environment) {
            // fda.create();
            IntStream.range(1, 10).forEach(s -> fda.insert());
        }
    }

    private static void resetLoggerToDefault() throws Exception {
        // Reset logger to standard mechanism
        // Ref : http://stackoverflow.com/questions/27356918/drop-wizard-request-response-logging
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        context.reset();
        ContextInitializer initializer = new ContextInitializer(context);
        initializer.autoConfig();
    }
}
