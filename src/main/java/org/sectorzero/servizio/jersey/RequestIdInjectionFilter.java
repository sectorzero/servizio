package org.sectorzero.servizio.jersey;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import java.util.UUID;

import javax.ws.rs.ext.Provider;

@Provider
public class RequestIdInjectionFilter implements ContainerRequestFilter, ContainerResponseFilter {

  static final String REQUEST_ID_HEADER_PREFIX = "x";
  static final String REQUEST_ID_HEADER_SUFFIX = "request-id";
  static final int MAX_REQUEST_ID_CONTEXT_LENGTH = 8;

  final String requestIdHeaderName;

  public RequestIdInjectionFilter(String context) {
    Validate.isTrue(StringUtils.isNotEmpty(context));
    Validate.isTrue(context.length() <= MAX_REQUEST_ID_CONTEXT_LENGTH);
    this.requestIdHeaderName = String.format(
        "%s-%s-%s",
        REQUEST_ID_HEADER_PREFIX, context, REQUEST_ID_HEADER_SUFFIX);
  }

  @Override
  public ContainerRequest filter(ContainerRequest request) {
    request.getRequestHeaders().add(requestIdHeaderName, UUID.randomUUID().toString());
    return request;
  }

  @Override
  public ContainerResponse filter(ContainerRequest request, ContainerResponse response) {
    response.getHttpHeaders().add(requestIdHeaderName, request.getHeaderValue(requestIdHeaderName));
    return response;
  }
}
