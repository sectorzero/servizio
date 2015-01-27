package com.fooorg.fooproj.app;

import com.fooorg.fooproj.configuration.FooModule;
import com.fooorg.fooproj.configuration.SampleServiceConfiguration;
import com.fooorg.fooproj.configuration.SampleServiceConfigurationModule;
import com.fooorg.fooproj.configuration.SampleServiceModule;

import com.fooorg.fooproj.core.FooConcept;

import com.fooorg.fooproj.resources.FooResource;
import com.fooorg.fooproj.resources.HolaResource;

import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import io.federecio.dropwizard.swagger.SwaggerDropwizard;

import lombok.extern.log4j.Log4j;

@Log4j
public class SampleService extends Application<SampleServiceConfiguration> {

    GuiceBundle<SampleServiceConfiguration> guiceBundle;
    SwaggerDropwizard swagger;

    public static void main(String[] args) throws Exception {
        new SampleService().run(args);
    }

    @Override
    public String getName() {
        return "Hola Sample Service";
    }

    @Override
    public void initialize(Bootstrap<SampleServiceConfiguration> bootstrap) {
        guiceBundle = GuiceBundle.<SampleServiceConfiguration>newBuilder()
                .addModule(new SampleServiceModule())
                .addModule(new SampleServiceConfigurationModule())
                .addModule(new FooModule())
                .setConfigClass(SampleServiceConfiguration.class)
                .build();
        bootstrap.addBundle(guiceBundle);

        swagger = new SwaggerDropwizard();
        swagger.onInitialize(bootstrap);
    }

    @Override
    public void run(SampleServiceConfiguration configuration, Environment environment) throws Exception {
        // Resources
        registerApiResourcesViaGuice(environment);

        // Other Application Modules not related to Resources
        prepareAppModules(environment);

        // Swagger
        swagger.onRun(configuration, environment);
    }

    private void registerApiResources(SampleServiceConfiguration configuration, Environment environment) {
        // /hola
        final HolaResource holaResource = new HolaResource(
                configuration.getTemplate(),
                configuration.getDefaultName());
        environment.jersey().register(holaResource);
    }

    // TODO :
    // Option A : Automatically register all resource classes with some annotation
    // Option B : Collect the classes to register via configuration
    // DO NOT USE dropwizard-guice's auto-config as it works wierdly
    private void registerApiResourcesViaGuice(Environment environment) {
        environment.jersey().register(HolaResource.class);
        environment.jersey().register(FooResource.class);
    }

    private void prepareAppModules(Environment environment) {
        // FooConcept fooInstance = guiceBundle.getInjector().getInstance(FooConcept.class);
        // log.info(String.format("FooConcept Object Instance: %s", fooInstance));
    }
}
