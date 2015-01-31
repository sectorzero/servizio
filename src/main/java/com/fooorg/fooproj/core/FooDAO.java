package com.fooorg.fooproj.core;

import com.fooorg.fooproj.model.Token;
import com.fooorg.fooproj.model.TokenMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import java.util.Iterator;

public interface FooDAO {

    @SqlUpdate("create table foodata (id int primary key, name varchar(100))")
    void createFooDataTable();

    @SqlUpdate("insert into foodata (id, name) values (:id, :name)")
    void insertIntoFooData(@Bind("id") int id, @Bind("name") String name);

    @SqlQuery("select id, name from foodata where id = :id")
    @Mapper(TokenMapper.class)
    Token findNameByIdFromFooData(@Bind("id") int id);

    @SqlQuery("select id, name from foodata")
    @Mapper(TokenMapper.class)
    Iterator<Token> findNamesFromFooData();

    /**
     * close with no args is used to close the connection
     */
    void close();
}
