package com.fooorg.fooproj.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;
import java.util.Map;

@Value
public class ModelComplexType {
    @JsonProperty
    String stringProp;

    @JsonProperty
    List<Integer> listProp;

    @JsonProperty
    Map<String, String> mapSimpleProp;

    @JsonProperty
    Map<String, ModelTypeA> mapComplexProp;
}
