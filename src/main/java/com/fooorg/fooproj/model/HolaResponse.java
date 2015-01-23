package com.fooorg.fooproj.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class HolaResponse {
    @JsonProperty
    long id;

    @JsonProperty
    String content;
}
