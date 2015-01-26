package com.fooorg.fooproj.core;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import lombok.Data;

@Data
@Singleton
public class FooConcept {
    private final FooDependencyA a;
    private final FooDependencyB b;
    private final AtomicInteger counter;

    @Inject
    public FooConcept(FooDependencyA a, FooDependencyB b, AtomicInteger counter) {
        this.a = a;
        this.b = b;
        this.counter = counter;
    }

    public String doSomething() {
        a.invoke();
        b.invoke();
        a.invoke();

        counter.incrementAndGet();

        return UUID.randomUUID().toString();
    }

}
