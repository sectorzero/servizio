package com.fooorg.fooproj.core;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

public interface FooDAO {

    @SqlUpdate("create table foodata (id int primary key, name varchar(100))")
    void createFooDataTable();

    @SqlUpdate("insert into foodata (id, name) values (:id, :name)")
    void insertIntoFooData(@Bind("id") int id, @Bind("name") String name);

    @SqlQuery("select name from foodata where id = :id")
    String findNameByIdFromFooData(@Bind("id") int id);

    /**
     * close with no args is used to close the connection
     */
    void close();
}
