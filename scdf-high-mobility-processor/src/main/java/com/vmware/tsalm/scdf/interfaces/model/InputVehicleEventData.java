package com.vmware.tsalm.scdf.interfaces.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InputVehicleEventData {
    private VehicleBaseData vehicle;
    private VehicleEvent event;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VehicleBaseData {
        private String vin;
        @JsonProperty("serial_number")
        private String serialNumber;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VehicleEvent {
        private String type;
        @JsonProperty("received_at")
        private Date receivedAt;
    }

    public boolean isVehicleLocationChangedEvent() {
        return "vehicle_location_changed".equals(event.type);
    }
}