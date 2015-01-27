package com.fooorg.fooproj.resources;

import com.fooorg.fooproj.core.FooConcept;
import com.fooorg.fooproj.model.FooResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import lombok.Value;

@Path("/foo")
@Api(value = "/foo", description = "Returns some internal values")
@Produces(MediaType.APPLICATION_JSON)
@Value
public class FooResource {

    FooConcept foo;

    @Inject
    public FooResource(FooConcept foo) {
        this.foo = foo;
    }

    @GET
    @ApiOperation(
            value = "Default path to get internal values",
            notes = "Return internal values for demo",
            response = FooResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid Message ( dummy for demo )"),
            @ApiResponse(code = 404, message = "Not Found ( dummy for demo )") })
    @Timed
    public FooResponse getAnswer() {
        return new FooResponse(foo.getA().getUrl(), foo.getB().getThreshold());
    }
}
