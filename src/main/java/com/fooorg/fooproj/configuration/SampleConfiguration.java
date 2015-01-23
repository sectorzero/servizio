package com.fooorg.fooproj.configuration;

import io.dropwizard.Configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SampleConfiguration extends Configuration {

    @Valid
    @NotNull
    @JsonProperty
    private String template;

    @Valid
    @NotNull
    @JsonProperty
    private String defaultName = "Stranger";

}
