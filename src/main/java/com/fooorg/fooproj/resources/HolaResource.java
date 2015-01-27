package com.fooorg.fooproj.resources;

import com.fooorg.fooproj.model.HolaResponse;

import java.util.concurrent.ThreadLocalRandom;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.wordnik.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;

import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import lombok.Value;

@Path("/hola")
@Api(value = "/hola", description = "Greets all aliens by name")
@Produces(MediaType.APPLICATION_JSON)
@Value
public class HolaResource {

    String template;
    String defaultName;

    @Inject
    public HolaResource(
            @Named("template") String template,
            @Named("defaultName") String defaultName)
    {
        this.template = template;
        this.defaultName = defaultName;
    }

    @GET
    @ApiOperation(
            value = "Default greeting operation",
            notes = "Either greets you by default name or your provided name",
            response = HolaResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid Message ( dummy for demo )"),
            @ApiResponse(code = 404, message = "Not Found ( dummy for demo )") })
    @Timed
    public HolaResponse greet(
            @ApiParam(value = "Name to be greeted with", defaultValue = "Amigo", required = false, allowMultiple = false) @QueryParam("name") String name) {
        final String value = String.format(template, (StringUtils.isEmpty(name) ? defaultName : name));
        return new HolaResponse(
                Math.abs(ThreadLocalRandom.current().nextInt()),
                value);
    }
}
