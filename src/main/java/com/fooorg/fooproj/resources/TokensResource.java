package com.fooorg.fooproj.resources;

import com.fooorg.fooproj.core.FooDAO;
import com.fooorg.fooproj.model.Token;

import java.util.Collection;
import java.util.Iterator;
import com.google.common.collect.ImmutableList;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.wordnik.swagger.annotations.*;
import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import lombok.Value;

// TODO : Convert exception to externally visible error/response code

@Path("/tokens")
@Api(value = "/tokens", description = "Demos an API which uses a database to put and get a resource")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Value
public class TokensResource {

    final FooDAO dao;

    @Inject
    public TokensResource(FooDAO dao) {
        this.dao = dao;
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
    @Timed
    public Token listTokenById(
            @ApiParam(value = "ID of token that needs to be fetched", allowableValues = "range[1,9999999999]", required = true) @PathParam("tokenId")  int id) {
        return dao.findNameByIdFromFooData(id);
    }

    @POST
    @ApiOperation(
            value = "Operation which adds a token",
            notes = "Adds a token received serialized as JSON")
    @ApiResponses(value = {
            @ApiResponse(code = 405, message = "Invalid Input ( dummy for demo )")})
    @Timed
    public Response addToken(
            @ApiParam(value = "Token that needs to be added to the store", required = true) Token token) {
        dao.insertIntoFooData(token.getId(), token.getToken());
        return Response.ok().entity("SUCCESS").build();
    }
}
