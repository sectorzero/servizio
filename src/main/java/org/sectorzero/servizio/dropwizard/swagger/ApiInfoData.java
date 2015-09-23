package org.sectorzero.servizio.dropwizard.swagger;

import com.wordnik.swagger.model.ApiInfo;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ApiInfoData {

    @Valid
    @NotNull
    @JsonProperty
    String apiVersion = "0.0";

    @Valid
    @NotNull
    @JsonProperty
    String title = "Base Service";

    @Valid
    @NotNull
    @JsonProperty
    String description = "Base Service Description";

    @Valid
    @NotNull
    @JsonProperty
    String termsOfServiceUrl = "https://tosdr.org/";

    @Valid
    @NotNull
    @JsonProperty
    String contact = "";

    @Valid
    @NotNull
    @JsonProperty
    String license = "http://en.wikipedia.org/wiki/Software_license";

    @Valid
    @NotNull
    @JsonProperty
    String licenseUrl = "http://en.wikipedia.org/wiki/Software_license";

    public static ApiInfo toApiInfo(ApiInfoData apiInfoData) {
        return new ApiInfo(
                apiInfoData.getTitle(),
                apiInfoData.getDescription(),
                apiInfoData.getTermsOfServiceUrl(),
                apiInfoData.getContact(),
                apiInfoData.getLicense(),
                apiInfoData.getLicenseUrl()
        );
    }
}
