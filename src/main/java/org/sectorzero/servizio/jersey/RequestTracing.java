package org.sectorzero.servizio.jersey;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;
import lombok.Data;

@Data
public class RequestTracing {

  @Valid
  @JsonProperty
  String requestIdContext = null;

  @Valid
  @JsonProperty
  boolean enableLogging = false;
}
