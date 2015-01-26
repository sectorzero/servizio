package com.fooorg.fooproj.core;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import lombok.Data;

@Data
public class FooDependencyA {
    private final String url;

    @Inject
    public FooDependencyA(@Named("url") String url) {
        this.url = url;
    }

    public void invoke() {
        // empty
    }
}
