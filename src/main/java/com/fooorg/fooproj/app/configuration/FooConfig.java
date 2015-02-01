package com.fooorg.fooproj.app.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FooConfig {

    @Valid
    @NotNull
    @JsonProperty
    public String url;

    @Valid
    @NotNull
    @JsonProperty
    public Double threshold;
}
