package com.vmware.tsalm.scdf.infrastructure.service;

import com.vmware.tsalm.scdf.domain.model.VehicleLocationData;
import com.vmware.tsalm.scdf.domain.model.service.VehicleInformationService;
import com.vmware.tsalm.scdf.infrastructure.JwtUtils;
import com.vmware.tsalm.scdf.infrastructure.service.model.HighMobilityConfigurationProperties;
import com.vmware.tsalm.scdf.infrastructure.service.model.HighMobilityVehicleLocationData;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class HighMobilityVehicleInformationService implements VehicleInformationService {

    private final RestTemplate restTemplate;
    private final HighMobilityConfigurationProperties configurationProperties;

    public HighMobilityVehicleInformationService(final RestTemplateBuilder restTemplateBuilder,
                                                 final HighMobilityConfigurationProperties configurationProperties) {
        this.restTemplate = restTemplateBuilder.build();
        this.configurationProperties = configurationProperties;
    }

    public VehicleLocationData fetchVehicleLocation() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(JwtUtils.createJwt(configurationProperties));
        final HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        return Objects.requireNonNull(restTemplate.exchange(configurationProperties.getBaseUrl() + "/vehicle_location",
                HttpMethod.GET, httpEntity, HighMobilityVehicleLocationData.class).getBody()).toDomain();
    }
}
