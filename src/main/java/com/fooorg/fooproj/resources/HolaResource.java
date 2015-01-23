package com.fooorg.fooproj.resources;

import com.fooorg.fooproj.model.HolaResponse;

import java.util.concurrent.ThreadLocalRandom;
import org.apache.commons.lang.StringUtils;

import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import lombok.Value;

@Path("/hola")
@Produces(MediaType.APPLICATION_JSON)
@Value
public class HolaResource {

    String template;
    String defaultName;

    @GET
    @Timed
    public HolaResponse greet(
            @QueryParam("name") String name) {
        final String value = String.format(template, (StringUtils.isEmpty(name) ? defaultName : name));
        return new HolaResponse(
                Math.abs(ThreadLocalRandom.current().nextInt()),
                value);
    }
}
