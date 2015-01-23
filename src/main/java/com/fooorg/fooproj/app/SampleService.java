package com.fooorg.fooproj.app;

import com.fooorg.fooproj.configuration.SampleConfiguration;
import com.fooorg.fooproj.resources.HolaResource;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class SampleService extends Application<SampleConfiguration> {

    public static void main(String[] args) throws Exception {
        new SampleService().run(args);
    }

    @Override
    public String getName() {
        return "Hola Sample Service";
    }

    @Override
    public void initialize(Bootstrap<SampleConfiguration> bootstrap) {
    }

    @Override
    public void run(SampleConfiguration configuration, Environment environment) throws Exception {
        registerApiResources(configuration, environment);
    }

    private void registerApiResources(SampleConfiguration configuration, Environment environment) {
        // /hola
        final HolaResource holaResource = new HolaResource(
                configuration.getTemplate(),
                configuration.getDefaultName());
        environment.jersey().register(holaResource);
    }
}
