package com.fooorg.fooproj.resources;

import com.fooorg.fooproj.model.ModelComplexType;
import com.fooorg.fooproj.model.ModelTypeA;

import java.util.Collection;
import java.util.UUID;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

import lombok.Value;

@Path("/complex")
@Api(value = "/complex", description = "Demos a complex object model request-response")
@Produces(MediaType.APPLICATION_JSON)
@Value
public class ComplexResource {

    @GET
    @Path("/single")
    @ApiOperation(
            value = "Operation which return a single instance of the complex data type",
            notes = "Returns a complex object serialized as JSON",
            response = ModelComplexType.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid Message ( dummy for demo )"),
            @ApiResponse(code = 404, message = "Not Found ( dummy for demo )") })
    @Timed
    public ModelComplexType single() {
        return generateRandomInstance();
    }

    @GET
    @Path("/collection")
    @ApiOperation(
            value = "Operation which returns a collection of instances of the complex data type",
            notes = "Returns a collection of complex object serialized as JSON",
            response = ModelComplexType.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid Message ( dummy for demo )"),
            @ApiResponse(code = 404, message = "Not Found ( dummy for demo )") })
    @Timed
    public Collection<ModelComplexType> collection() {
        return new ImmutableList.Builder<ModelComplexType>()
                .add(generateRandomInstance())
                .add(generateRandomInstance())
                .add(generateRandomInstance())
                .build();
    }

    private ModelComplexType generateRandomInstance() {
        return new ModelComplexType(
                UUID.randomUUID().toString(),
                new ImmutableList.Builder<Integer>()
                        .add(7919)
                        .add(7909)
                        .add(7901)
                        .build(),
                new ImmutableMap.Builder<String, String>()
                        .put("Washington", "Seattle")
                        .put("Oregon", "Portland")
                        .put("California", "San Francisco")
                        .build(),
                new ImmutableMap.Builder<String, ModelTypeA>()
                        .put("MathematicalConstants",
                                new ModelTypeA(new ImmutableMap.Builder<String, Double>()
                                        .put("ItemOne", 3.142)
                                        .put("ItemTwo", 2.718)
                                        .put("ItemThree", 1.618)
                                        .build()))
                        .put("PhysicalConstants",
                                new ModelTypeA(new ImmutableMap.Builder<String, Double>()
                                        .put("ItemOne", 2.997)
                                        .put("ItemTwo", 6.626)
                                        .put("ItemThree", 6.672)
                                        .build()))
                        .build());

      }
}
