package com.fooorg.fooproj.app.configuration.guice;

import com.fooorg.fooproj.app.configuration.SampleServiceConfiguration;
import com.google.inject.AbstractModule;

import com.google.inject.Provides;
import com.google.inject.name.Named;

public class FooModule extends AbstractModule {

    @Override
    protected void configure() {
    }

    @Provides
    @Named("url")
    public String getUrl(SampleServiceConfiguration configuration) {
       return configuration.getFooConfig().getUrl();
    }

    @Provides
    @Named("threshold")
    public double getThreshold(SampleServiceConfiguration configuration) {
        return configuration.getFooConfig().getThreshold();
    }
}
