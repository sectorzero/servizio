package com.fooorg.fooproj.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import lombok.Value;

@Value
public class Token {

    @JsonProperty
    int id;

    @JsonProperty
    String token;

}
