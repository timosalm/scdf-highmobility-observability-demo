package com.vmware.tsalm.scdf.infrastructure.service.model;

import com.vmware.tsalm.scdf.infrastructure.JwtConfigurationProperties;
import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@ConfigurationProperties("high-mobility-api")
@Data
@Validated
@NoArgsConstructor
public class HighMobilityConfigurationProperties implements JwtConfigurationProperties {

    @NotEmpty
    private String apiVersion;

    @NotEmpty
    private String appId;

    @NotEmpty
    private String vehicleAccessToken;

    @NotEmpty
    private String aud;

    @NotEmpty
    private String iss;

    @NotEmpty
    private String privateKeyString;

    public String getBaseUrl() {
        return getAud();
    }
}
