package org.sectorzero.servizio.app.configuration.guice;

import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.ManagedDataSource;
import io.dropwizard.jdbi.DBIHealthCheck;
import io.dropwizard.setup.Environment;

import org.sectorzero.servizio.dropwizard.jdbi.CustomDBI;
import org.sectorzero.servizio.dropwizard.jdbi.CustomDBIFactory;
import org.skife.jdbi.v2.DBI;
import javax.sql.DataSource;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import lombok.extern.log4j.Log4j;

@Log4j
public class JdbcDataSourceModule extends AbstractModule {

    @Override
    protected void configure() {
    }

    @Provides
    @Singleton
    public DataSource getDataSource(CustomDBI customDBI) throws Exception {
        return customDBI.getManagedDataSource();
    }

    @Provides
    @Singleton
    public DBI getDBI(CustomDBI customDBI) throws Exception {
        return customDBI.getDbi();
    }

    @Provides
    @Singleton
    public CustomDBI getCustomDBI(
            Environment environment,
            DataSourceFactory configuration,
            @Named("JdbcDataSourceName") String name) throws Exception {

        // Managed DataSource
        ManagedDataSource managedDataSource = configuration.build(environment.metrics(), name);
        environment.lifecycle().manage(managedDataSource);

        // DBI
        CustomDBIFactory factory = new CustomDBIFactory();
        DBI dbi = factory.build(environment, configuration, managedDataSource, name);

        // DB HealthCheck
        DBIHealthCheck healthCheck = new DBIHealthCheck(dbi, configuration.getValidationQuery());
        environment.healthChecks().register(name, healthCheck);

        // Container
        CustomDBI customDBI = new CustomDBI(managedDataSource, dbi);
        return customDBI;
    }
}
