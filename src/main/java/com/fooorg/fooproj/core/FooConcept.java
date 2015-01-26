package com.fooorg.fooproj.core;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import lombok.Data;

@Data
@Singleton
public class FooConcept {
    private FooDependencyA a;
    private FooDependencyB b;

    @Inject
    public FooConcept(FooDependencyA a, FooDependencyB b) {
        this.a = a;
        this.b = b;
    }
}
