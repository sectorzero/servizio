package org.sectorzero.server.framework.dropwizard.app.configuration;

import org.sectorzero.server.framework.dropwizard.swagger.SwaggerSetup;

import io.dropwizard.Configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BaseConfiguration extends Configuration {

    @Valid
    @NotNull
    @JsonProperty
    private SwaggerSetup swaggerSetup = new SwaggerSetup();

}
