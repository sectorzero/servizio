package org.sectorzero.servizio.app;

import org.sectorzero.servizio.jersey.RequestTracingBundle;

import org.sectorzero.servizio.app.configuration.BaseConfiguration;
import org.sectorzero.servizio.dropwizard.guice.AbstractDropwizardModule;
import org.sectorzero.servizio.dropwizard.guice.GuiceApplication;
import org.sectorzero.servizio.dropwizard.guice.support.GuiceSupport;

import io.dropwizard.setup.Bootstrap;
import io.dropwizard.views.ViewBundle;
import io.dropwizard.lifecycle.Managed;

import java.util.List;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.util.ContextInitializer;

import com.google.inject.AbstractModule;

public abstract class BaseService<T extends BaseConfiguration> extends GuiceApplication<T> {

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
                addBundle(JMXMPJmxConnectorServerBundle.class);
                addBundle(ViewBundle.class);
                addBundle(SwaggerBundle.class);
                addBundle(JerseyWadlBundle.class);
                addBundle(RequestTracingBundle.class);
            }
        });

        // User Application Guice Modules
        for(AbstractModule module : userGuiceModules()) {
            guiceBuilder.addModule(module);
        }

        // User Application 'Managed' Modules
        guiceBuilder.addModule(new AbstractDropwizardModule() {
            @Override
            protected void configureModule() {
                for(Class<? extends Managed> m : userLifecycleManagedClasses()) {
                    lifecycle().manage(m);
                }
            }
        });

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
     * Provide the Lifecycle 'Managed' objects to be available by your application
     * @return
     */
    protected abstract List<Class <? extends Managed>> userLifecycleManagedClasses();

    /**
     * Provide the REST enabled resources to be exposed by your application
     * @return
     */
    protected abstract List<Class> userResourceClasses();

    /**
     * Reset logger to standard mechanism
     * Ref : http://stackoverflow.com/questions/27356918/drop-wizard-request-response-logging
     */
    private void setupLogging() {
        try {
            LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
            context.reset();
            ContextInitializer initializer = new ContextInitializer(context);
            initializer.autoConfig();
        } catch(Exception e) {
            throw new RuntimeException("Exception setting-up/initializing Logging", e);
        }
    }

}
