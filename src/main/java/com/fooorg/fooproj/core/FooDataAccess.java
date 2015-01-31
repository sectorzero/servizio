package com.fooorg.fooproj.core;

import com.google.inject.Inject;
import lombok.extern.log4j.Log4j;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Log4j
public class FooDataAccess {

    final FooDAO dao;

    @Inject
    public FooDataAccess(FooDAO dao) {
        this.dao = dao;
    }

    public void create() {
        try {
            log.info("Executing createFooDataTable on FooData");
            dao.createFooDataTable();
            log.info("Successful : Executing createFooDataTable on FooData");
        } catch(Exception e) {
            log.error("Error : Executing createFooDataTable on FooData");
            throw e;
        }
    }

    public void insert() {
        try {
            log.info("Executing insert on FooData");
            dao.insertIntoFooData(Math.abs(ThreadLocalRandom.current().nextInt()), UUID.randomUUID().toString());
            log.info("Successful : Executing insert on FooData");
        } catch(Exception e) {
            log.error("Error : Executing insert on FooData");
            throw e;
        }
    }
}
