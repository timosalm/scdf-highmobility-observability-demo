package com.vmware.tsalm.scdf.interfaces.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@Value
public class InputVehicleEventData {
    VehicleBaseData vehicle;
    VehicleEvent event;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Value
    public static class VehicleBaseData {
        String vin;
        @JsonProperty("serial_number")
        String serialNumber;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Value
    public static class VehicleEvent {
        String type;
        @JsonProperty("received_at")
        Date receivedAt;
    }

    public boolean isVehicleLocationChangedEvent() {
        return "vehicle_location_changed".equals(event.type);
    }
}
