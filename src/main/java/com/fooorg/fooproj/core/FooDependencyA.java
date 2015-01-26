package com.fooorg.fooproj.core;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import lombok.Value;

@Value
public class FooDependencyA {
    String url;

    @Inject
    public FooDependencyA(@Named("url") String url) {
        this.url = url;
    }
}
