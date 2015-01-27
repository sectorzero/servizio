package com.fooorg.fooproj.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.Map;

@Value
public class ModelTypeA {
    @JsonProperty
    Map<String, Double> data;
}
