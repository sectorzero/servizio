package com.fooorg.fooproj.app.configuration.guice;

import com.fooorg.fooproj.app.configuration.SampleServiceConfiguration;
import com.fooorg.fooproj.core.FooDAO;

import io.dropwizard.jdbi.DBIFactory;
import org.skife.jdbi.v2.DBI;

import io.dropwizard.setup.Environment;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class SampleServiceModule extends AbstractModule {

    @Override
    protected void configure() {
    }

    @Provides @Singleton
    public DBI getJDBI(SampleServiceConfiguration configuration, Environment environment) throws Exception {
        final DBIFactory factory = new DBIFactory();
        return factory.build(environment, configuration.getDataSourceFactory(), "sample-jdbi-mysql");
    }

    @Provides
    public FooDAO getFooDAO(DBI jdbi) {
        return jdbi.onDemand(FooDAO.class);
    }

}
