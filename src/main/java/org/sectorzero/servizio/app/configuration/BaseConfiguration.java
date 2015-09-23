package org.sectorzero.servizio.app.configuration;

import org.sectorzero.servizio.dropwizard.swagger.ApiInfoData;
import org.sectorzero.servizio.dropwizard.swagger.SwaggerSetup;

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

    @Valid
    @NotNull
    @JsonProperty
    private ApiInfoData apiInfoData = new ApiInfoData();

}
