package org.sectorzero.servizio.dropwizard.jdbi;

import io.dropwizard.db.ManagedDataSource;
import org.skife.jdbi.v2.DBI;

import lombok.Value;

@Value
public class CustomDBI {
    ManagedDataSource managedDataSource;
    DBI dbi;
}
