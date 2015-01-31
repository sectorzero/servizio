package com.fooorg.fooproj.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Token {

    @JsonProperty
    int id;

    @JsonProperty
    String token;

}
