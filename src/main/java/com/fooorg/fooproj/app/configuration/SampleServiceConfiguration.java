package com.fooorg.fooproj.app.configuration;

import io.dropwizard.db.DataSourceFactory;

import org.sectorzero.server.framework.dropwizard.app.configuration.BaseConfiguration;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SampleServiceConfiguration extends BaseConfiguration {

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
