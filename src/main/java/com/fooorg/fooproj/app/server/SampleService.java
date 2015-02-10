package com.fooorg.fooproj.app.server;

import com.fooorg.fooproj.app.configuration.guice.FooModule;
import com.fooorg.fooproj.app.configuration.SampleServiceConfiguration;
import com.fooorg.fooproj.app.configuration.guice.SampleServiceConfigurationModule;
import com.fooorg.fooproj.app.configuration.guice.SampleServiceModule;

import com.fooorg.fooproj.core.LifecycleManagedExample;
import com.fooorg.fooproj.resources.ComplexResource;
import com.fooorg.fooproj.resources.FooResource;
import com.fooorg.fooproj.resources.HolaResource;
import com.fooorg.fooproj.resources.TokensResource;

import io.dropwizard.lifecycle.Managed;
import org.sectorzero.server.framework.dropwizard.app.BaseService;

import com.google.inject.AbstractModule;

import com.google.common.collect.ImmutableList;
import java.util.List;

import com.fooorg.fooproj.core.FooDataAccess;
import java.util.stream.IntStream;

public class SampleService extends BaseService<SampleServiceConfiguration> {

    public static void main(String[] args) throws Exception {
        new SampleService().run(args);
    }

    @Override
    public String getName() {
        return "Sample Service";
    }

    @Override
    protected List<AbstractModule> userGuiceModules() {
        return new ImmutableList.Builder<AbstractModule>()
                .add(new SampleServiceConfigurationModule())
                .add(new SampleServiceModule())
                .add(new FooModule())
                .build();
    }

    @Override
    protected List<Class<? extends Managed>> userLifecycleManagedClasses() {
        return new ImmutableList.Builder<Class <? extends Managed>>()
                .add(LifecycleManagedExample.class)
                .build();
    }

    @Override
    protected List<Class> userResourceClasses() {
        return new ImmutableList.Builder<Class>()
                .add(HolaResource.class)
                .add(FooResource.class)
                .add(ComplexResource.class)
                .add(TokensResource.class)
                .build();
    }

//    private static class FooDataBundle extends RuntimeBundle {
//        final FooDataAccess fda;
//        @Inject
//        public FooDataBundle(FooDataAccess fda) {
//            this.fda = fda;
//        }
//        @Override
//        public void run(Environment environment) {
//            // fda.create();
//            IntStream.range(1, 10).forEach(s -> fda.insert());
//        }
//    }

}
