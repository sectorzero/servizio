package com.fooorg.fooproj.app.configuration;

import io.dropwizard.Configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.dropwizard.db.DataSourceFactory;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SampleServiceConfiguration extends Configuration {

    @Valid
    @NotNull
    @JsonProperty
    private String template;

    @Valid
    @NotNull
    @JsonProperty
    private String defaultName = "Stranger";

    @Valid
    @NotNull
    @JsonProperty
    private FooConfig fooConfig;

    @Valid
    @NotNull
    @JsonProperty
    private DataSourceFactory dataSourceFactory;

}
