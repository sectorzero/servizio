package com.fooorg.fooproj.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class FooResponse {
    @JsonProperty
    String url;

    @JsonProperty
    double threshold;
}
