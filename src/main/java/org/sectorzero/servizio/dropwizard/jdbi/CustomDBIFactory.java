package org.sectorzero.servizio.dropwizard.jdbi;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.codahale.metrics.jdbi.InstrumentedTimingCollector;
import com.codahale.metrics.jdbi.strategies.DelegatingStatementNameStrategy;
import com.codahale.metrics.jdbi.strategies.NameStrategies;
import com.codahale.metrics.jdbi.strategies.StatementNameStrategy;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.ManagedDataSource;
import io.dropwizard.jdbi.*;
import io.dropwizard.jdbi.args.JodaDateTimeArgumentFactory;
import io.dropwizard.jdbi.args.JodaDateTimeMapper;
import io.dropwizard.jdbi.args.OptionalArgumentFactory;
import io.dropwizard.jdbi.logging.LogbackLog;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.Binding;
import org.skife.jdbi.v2.ColonPrefixNamedParamStatementRewriter;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.RewrittenStatement;
import org.skife.jdbi.v2.tweak.StatementRewriter;
import org.slf4j.LoggerFactory;

import static com.codahale.metrics.MetricRegistry.name;

public class CustomDBIFactory extends DBIFactory {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(DBI.class);
    private static final String RAW_SQL = name(DBI.class, "raw-sql");

    private static class SanerNamingStrategy extends DelegatingStatementNameStrategy {
        private SanerNamingStrategy() {
            super(NameStrategies.CHECK_EMPTY,
                    NameStrategies.CONTEXT_CLASS,
                    NameStrategies.CONTEXT_NAME,
                    NameStrategies.SQL_OBJECT,
                    new StatementNameStrategy() {
                        @Override
                        public String getStatementName(StatementContext statementContext) {
                            return RAW_SQL;
                        }
                    });
        }
    }

    private static class NamePrependingStatementRewriter implements StatementRewriter {
        private final StatementRewriter rewriter;

        private NamePrependingStatementRewriter(StatementRewriter rewriter) {
            this.rewriter = rewriter;
        }

        @Override
        public RewrittenStatement rewrite(String sql, Binding params, StatementContext ctx) {
            if ((ctx.getSqlObjectType() != null) && (ctx.getSqlObjectMethod() != null)) {
                final StringBuilder query = new StringBuilder(sql.length() + 100);
                query.append("/* ");
                final String className = ctx.getSqlObjectType().getSimpleName();
                if (!className.isEmpty()) {
                    query.append(className).append('.');
                }
                query.append(ctx.getSqlObjectMethod().getName());
                query.append(" */ ");
                query.append(sql);
                return rewriter.rewrite(query.toString(), params, ctx);
            }
            return rewriter.rewrite(sql, params, ctx);
        }
    }

    /**
     * The DBIFactory provided tightly coupled managing the DataSource which might not be
     * ideal when trying to use DataSource independent of DBI. This builder method takes the
     * management of the DataSource object outside and makes it the responsiblity of the application
     *
     * @param environment
     * @param configuration
     * @param dataSource
     * @param name
     * @return
     */
    @Override
    public DBI build(Environment environment,
                     DataSourceFactory configuration,
                     ManagedDataSource dataSource,
                     String name) {
        // final String validationQuery = configuration.getValidationQuery();
        final DBI dbi = new DBI(dataSource);
        dbi.setSQLLog(new LogbackLog(LOGGER, Level.TRACE));
        dbi.setTimingCollector(new InstrumentedTimingCollector(environment.metrics(),
                new SanerNamingStrategy()));
        if (configuration.isAutoCommentsEnabled()) {
            dbi.setStatementRewriter(new NamePrependingStatementRewriter(new ColonPrefixNamedParamStatementRewriter()));
        }
        dbi.registerArgumentFactory(new OptionalArgumentFactory(configuration.getDriverClass()));
        dbi.registerContainerFactory(new ImmutableListContainerFactory());
        dbi.registerContainerFactory(new ImmutableSetContainerFactory());
        dbi.registerContainerFactory(new OptionalContainerFactory());
        dbi.registerArgumentFactory(new JodaDateTimeArgumentFactory());
        dbi.registerMapper(new JodaDateTimeMapper());

        return dbi;
    }
}
