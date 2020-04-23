package com.vmware.tsalm.scdf.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vmware.tsalm.scdf.Constants;
import lombok.Value;

import java.util.Date;

@Value
public class VehicleLocationData {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_FORMAT)
    Date createdAt;

    Double latitude;
    Double longitude;
}
