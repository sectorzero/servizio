package com.fooorg.fooproj.resources;

import com.fooorg.fooproj.core.FooDAO;
import com.fooorg.fooproj.model.Token;

import org.junit.Ignore;
import org.mariadb.jdbc.MySQLDataSource;
import org.skife.jdbi.v2.DBI;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import com.fooorg.fooproj.framework.FooProjIntegrationTest;

import org.junit.experimental.categories.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

@Category(FooProjIntegrationTest.class)
public class TokensResourceTest {

    TokensResource resource;

    @Before
    public void setup() {
        MySQLDataSource ds = new MySQLDataSource();
        ds.setUserName("root");
        ds.setPassword("insecure");
        ds.setUrl("jdbc:mysql://localhost:3306/sampleservice_test");

        DBI jdbi = new DBI(ds);
        FooDAO dao = jdbi.onDemand(FooDAO.class);

        resource = new TokensResource(dao);
    }

    @Test
    public void fetchTokenById() {
        // Arrange
        // Act
        Token t = resource.listTokenById(180130466);
        // Assert
        assertNotNull(t);
        assertEquals(180130466, t.getId());
        assertEquals("424db605-d745-4b53-b77a-4b1391dbf4d4", t.getToken());
    }

    @Test
    public void fetchAllTokens() {
        // Arrange
        // Act
        Collection<Token> tokens = resource.listTokens();
        // Assert
        assertNotNull(tokens);
        assertEquals(82, tokens.size());
    }

    @Ignore
    @Test
    public void createToken() {
        // Arrange
        // Act
        resource.addToken(new Token(
                Math.abs(ThreadLocalRandom.current().nextInt()),
                UUID.randomUUID().toString()));
        // Assert
    }
}