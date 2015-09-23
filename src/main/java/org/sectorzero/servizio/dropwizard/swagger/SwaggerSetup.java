package org.sectorzero.servizio.dropwizard.swagger;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class SwaggerSetup {

    @Valid
    @NotNull
    @JsonProperty
    private String endpointOverride = "";

}
