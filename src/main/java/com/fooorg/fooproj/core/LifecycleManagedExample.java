package com.fooorg.fooproj.core;

import io.dropwizard.lifecycle.Managed;

import com.google.inject.Inject;

import lombok.extern.log4j.Log4j;

@Log4j
public class LifecycleManagedExample implements Managed {

    final FooConcept fooConcept;

    @Inject
    public LifecycleManagedExample(FooConcept fooConcept) {
        this.fooConcept = fooConcept;
    }

    @Override
    public void start() throws Exception {
        log.info("[START] : LifecycleManagedExample");
    }

    @Override
    public void stop() throws Exception {
        log.info("[STOP] : LifecycleManagedExample");
    }
}
