package com.fooorg.fooproj.app.configuration.guice;

import com.fooorg.fooproj.app.configuration.SampleServiceConfiguration;
import com.google.inject.AbstractModule;

import com.google.inject.Provides;
import com.google.inject.name.Named;

public class SampleServiceConfigurationModule extends AbstractModule {

    @Override
    protected void configure() {
    }

    @Provides
    @Named("template")
    public String getTemplate(SampleServiceConfiguration config) {
        return config.getTemplate();
    }

    @Provides
    @Named("defaultName")
    public String getDefaultName(SampleServiceConfiguration config) {
        return config.getDefaultName();
    }

}
