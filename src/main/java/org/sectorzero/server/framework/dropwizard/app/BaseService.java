package org.sectorzero.server.framework.dropwizard.app;

import io.dropwizard.setup.Bootstrap;
import org.sectorzero.server.framework.dropwizard.guice.AbstractDropwizardModule;
import org.sectorzero.server.framework.dropwizard.guice.GuiceApplication;
import org.sectorzero.server.framework.dropwizard.guice.RuntimeBundle;
import org.sectorzero.server.framework.dropwizard.guice.support.GuiceSupport;

import org.sectorzero.server.framework.dropwizard.swagger.SwaggerDropwizard;

import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

import java.util.List;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.util.ContextInitializer;

public abstract class BaseService<T extends Configuration> extends GuiceApplication<T> {

    @Override
    public void initialize(Bootstrap<T> bootstrap) {
    }

    @Override
    public void configure(final T configuration, GuiceSupport.Builder<T> guiceBuilder) {

        // Logging
        setupLogging();

        // Swagger
        guiceBuilder.addModule(new AbstractDropwizardModule() {
            @Override
            protected void configureModule() {
                // Swagger Via Guice
                addBundle(ViewBundle.class);
                addBundle(SwaggerBundle.class);
            }
        });

        // User Application Guice Modules
        for(AbstractModule module : userGuiceModules()) {
            guiceBuilder.addModule(module);
        }

        // User Application 'Resource' Modules
        guiceBuilder.addModule(new AbstractDropwizardModule() {
            @Override
            protected void configureModule() {
                for(Class c : userResourceClasses()) {
                    jersey().register(c);
                }
            }
        });
    }

    /**
     * Provide the Guice 'AbstractModule's which define the custom
     * Object instance bindings of your application
     */
    protected abstract List<AbstractModule> userGuiceModules();

    /**
     * Provide the REST enabled resources to be exposed by your application
     * @return
     */
    protected abstract List<Class> userResourceClasses();

    /**
     * Reset logger to standard mechanism
     * Ref : http://stackoverflow.com/questions/27356918/drop-wizard-request-response-logging
     */
    private static void setupLogging() {
        try {
            LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
            context.reset();
            ContextInitializer initializer = new ContextInitializer(context);
            initializer.autoConfig();
        } catch(Exception e) {
            System.exit(1);
        }
    }

    /**
     * Runtime setup to initialize and start Swagger components
     */
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
}