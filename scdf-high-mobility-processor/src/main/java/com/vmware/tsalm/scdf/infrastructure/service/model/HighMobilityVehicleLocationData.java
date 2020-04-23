package com.vmware.tsalm.scdf.infrastructure.service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vmware.tsalm.scdf.domain.model.VehicleLocationData;
import lombok.Getter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static com.vmware.tsalm.scdf.Constants.DATE_FORMAT;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class HighMobilityVehicleLocationData {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    private Date createdAt;
    private Double latitude;
    private Double longitude;

    @SuppressWarnings("unchecked")
    @JsonProperty("coordinates")
    private void unpackNested(Map<String, Object> coordinates) throws ParseException {
        this.createdAt = new SimpleDateFormat(DATE_FORMAT).parse((String)coordinates.get("timestamp"));
        final Map<String, Double> value = (Map<String, Double>) coordinates.get("value");
        this.latitude = value.get("latitude");
        this.longitude = value.get("longitude");
    }

    public VehicleLocationData toDomain() {
        return new VehicleLocationData(createdAt, latitude, longitude);
    }
}
