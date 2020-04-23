package com.vmware.tsalm.scdf.interfaces.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.vmware.tsalm.scdf.Constants;
import com.vmware.tsalm.scdf.domain.model.VehicleLocationData;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Value
public class OutputVehicleEventData {

    String eventType;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_FORMAT)
    Date receivedAt;
    String vin;
    String serialNumber;
    VehicleLocationData location;

    public OutputVehicleEventData(InputVehicleEventData inputData) {
        this(inputData, null);
    }

    public OutputVehicleEventData(InputVehicleEventData inputData, VehicleLocationData vehicleLocationData) {
        this(inputData.getEvent().getType(), inputData.getEvent().getReceivedAt(), inputData.getVehicle().getVin(),
                inputData.getVehicle().getSerialNumber(), vehicleLocationData);
    }
}
