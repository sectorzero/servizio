package com.fooorg.fooproj.resources;

import com.fooorg.fooproj.core.FooConcept;
import com.fooorg.fooproj.model.FooResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import lombok.Value;

@Path("/foo")
@Produces(MediaType.APPLICATION_JSON)
@Value
public class FooResource {

    FooConcept foo;

    @Inject
    public FooResource(FooConcept foo) {
        this.foo = foo;
    }

    @GET
    @Timed
    public FooResponse getAnswer() {
        return new FooResponse(foo.getA().getUrl(), foo.getB().getThreshold());
    }
}
