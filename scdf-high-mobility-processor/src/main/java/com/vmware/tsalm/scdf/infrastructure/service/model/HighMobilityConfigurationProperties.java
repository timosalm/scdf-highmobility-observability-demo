package com.vmware.tsalm.scdf.infrastructure.service.model;

import com.vmware.tsalm.scdf.infrastructure.JwtConfigurationProperties;
import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("high-mobility-api")
@Data
@NoArgsConstructor
public class HighMobilityConfigurationProperties implements JwtConfigurationProperties {
    private String apiVersion;
    private String appId;
    private String vehicleAccessToken;
    private String aud;
    private String iss;
    private String privateKeyString;

    public String getBaseUrl() {
        return getAud();
    }
}
