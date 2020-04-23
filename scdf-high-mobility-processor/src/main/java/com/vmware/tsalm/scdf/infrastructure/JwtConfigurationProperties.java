package com.vmware.tsalm.scdf.infrastructure;

public interface JwtConfigurationProperties {

    String getApiVersion();
    String getAppId();
    String getVehicleAccessToken();
    String getAud();
    String getIss();
    String getPrivateKeyString();
}
