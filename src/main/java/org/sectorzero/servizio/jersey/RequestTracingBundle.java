package org.sectorzero.servizio.jersey;

import com.sun.jersey.api.container.filter.LoggingFilter;

import org.sectorzero.servizio.dropwizard.guice.RuntimeBundle;
import io.dropwizard.setup.Environment;

import org.apache.commons.lang.StringUtils;

import com.google.inject.Inject;

public class RequestTracingBundle extends RuntimeBundle {

  final RequestTracing requestTracing;

  @Inject
  public RequestTracingBundle(RequestTracing configuration) {
    this.requestTracing = configuration;
  }

  @Override public void run(Environment environment) {
    if(!StringUtils.isEmpty(requestTracing.getRequestIdContext())) {
      RequestIdInjectionFilter requestIdInjectionFilter =
          new RequestIdInjectionFilter(requestTracing.getRequestIdContext());
      environment.jersey().getResourceConfig().getContainerRequestFilters().add(requestIdInjectionFilter);
      environment.jersey().getResourceConfig().getContainerResponseFilters().add(requestIdInjectionFilter);
    }

    if(requestTracing.enableLogging) {
      LoggingFilter loggingFilter = new LoggingFilter();
      environment.jersey().enable(LoggingFilter.FEATURE_LOGGING_DISABLE_ENTITY);
      environment.jersey().getResourceConfig().getContainerRequestFilters().add(loggingFilter);
      environment.jersey().getResourceConfig().getContainerResponseFilters().add(loggingFilter);
    }
  }
}
