package com.fooorg.fooproj.admin;

import com.google.inject.Singleton;
import com.hubspot.dropwizard.guice.InjectableHealthCheck;

import com.google.inject.Inject;

@Singleton
public class TemplateHealthCheck  extends InjectableHealthCheck {

    @Inject
    public TemplateHealthCheck() {}

    @Override
    public String getName() {
        return "TEST";
    }

    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}
