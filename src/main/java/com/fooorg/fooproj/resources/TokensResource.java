package com.fooorg.fooproj.resources;

import com.fooorg.fooproj.core.FooDAO;
import com.fooorg.fooproj.model.Token;

import java.util.Collection;
import java.util.Iterator;
import com.google.common.collect.ImmutableList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.wordnik.swagger.annotations.*;
import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import lombok.Value;

// TODO : Convert exception to externally visible error/response code

@Path("/tokens")
@Api(value = "/tokens", description = "Demos an API which uses a database to put and get a resource")
@Produces(MediaType.APPLICATION_JSON)
@Value
public class TokensResource {

    final FooDAO dao;

    @Inject
    public TokensResource(FooDAO dao) {
        this.dao = dao;
    }

    public void addToken(Token token) {
        dao.insertIntoFooData(token.getId(), token.getToken());
    }

    @GET
    @ApiOperation(
            value = "Operation which returns all tokens in the datastore",
            notes = "Returns a collection of tokens serialized as JSON",
            response = Token.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid Message ( dummy for demo )"),
            @ApiResponse(code = 404, message = "Not Found ( dummy for demo )") })
    @Timed
    public Collection<Token> listTokens() {
        Iterator<Token> tokens = dao.findNamesFromFooData();
        return new ImmutableList.Builder<Token>().addAll(tokens).build();
    }

    @GET
    @Path("/{tokenId}")
    @ApiOperation(
            value = "Operation which returns a tokens by Id",
            notes = "Returns a token serialized as JSON",
            response = Token.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid Message ( dummy for demo )"),
            @ApiResponse(code = 404, message = "Not Found ( dummy for demo )") })
    public Token listTokenById(
            @ApiParam(value = "ID of token that needs to be fetched", allowableValues = "range[1,9999999999]", required = true) @PathParam("tokenId")  int id) {
        return dao.findNameByIdFromFooData(id);
    }

}
