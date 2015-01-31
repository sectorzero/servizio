package com.fooorg.fooproj.model;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TokenMapper implements ResultSetMapper<Token> {
    @Override
    public Token map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new Token(r.getInt("id"), r.getString("name"));
    }
}
