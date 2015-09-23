package org.sectorzero.servizio.app;

import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;
import org.sectorzero.servizio.dropwizard.guice.RuntimeBundle;
import org.sectorzero.servizio.dropwizard.swagger.SwaggerDropwizard;

import com.google.inject.Inject;

/**
 * Runtime setup to initialize and start Swagger components
 */
public class SwaggerBundle extends RuntimeBundle {
    final Configuration configuration;
    final SwaggerDropwizard swagger;

    @Inject
    public SwaggerBundle(Configuration configuration, SwaggerDropwizard swagger) {
        this.configuration = configuration;
        this.swagger = swagger;
    }

    @Override
    public void run(Environment environment) {
        try {
            swagger.onRun(configuration, environment);
        } catch (Exception e) {
            throw new RuntimeException("Exception initializing Swagger components", e);
        }
    }
}
