package org.sectorzero.servizio.app;

import com.sun.jersey.api.core.ResourceConfig;

import org.sectorzero.servizio.dropwizard.guice.RuntimeBundle;
import io.dropwizard.setup.Environment;

public class JerseyWadlBundle extends RuntimeBundle {
  @Override
  public void run(Environment environment) {
    environment.jersey().disable(ResourceConfig.FEATURE_DISABLE_WADL);
  }
}
