package com.vmware.tsalm.scdf.interfaces;

import com.vmware.tsalm.scdf.domain.model.VehicleLocationData;
import com.vmware.tsalm.scdf.domain.model.service.VehicleInformationService;
import com.vmware.tsalm.scdf.interfaces.model.InputVehicleEventData;
import com.vmware.tsalm.scdf.interfaces.model.OutputVehicleEventData;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@RequiredArgsConstructor
@Component
public class VehicleEventProcessor {

    private final VehicleInformationService vehicleInformationService;

    @Bean
    Function<InputVehicleEventData, OutputVehicleEventData> processData() {
        return inputData -> {
            if (inputData.isVehicleLocationChangedEvent()) {
                final VehicleLocationData vehicleLocationData = vehicleInformationService.fetchVehicleLocation();
                return new OutputVehicleEventData(inputData, vehicleLocationData);
            }
            return new OutputVehicleEventData(inputData);
        };
    }
}
