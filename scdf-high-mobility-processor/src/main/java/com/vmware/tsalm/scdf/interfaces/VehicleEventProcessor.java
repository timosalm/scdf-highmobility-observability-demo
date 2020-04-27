package com.vmware.tsalm.scdf.interfaces;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmware.tsalm.scdf.domain.model.VehicleLocationData;
import com.vmware.tsalm.scdf.domain.model.service.VehicleInformationService;
import com.vmware.tsalm.scdf.interfaces.model.InputVehicleEventData;
import com.vmware.tsalm.scdf.interfaces.model.OutputVehicleEventData;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@RequiredArgsConstructor
@Component
public class VehicleEventProcessor {

    private final VehicleInformationService vehicleInformationService;

    @Bean
    // TODO Find better way to map the input payload. InputVehicleEventData and Message<InputVehicleEventData> didn't work.
    Function<String, OutputVehicleEventData> processData() {
        return inputDataString -> {
            final InputVehicleEventData inputData = getInputDataFromString(inputDataString);
            if (inputData.isVehicleLocationChangedEvent()) {
                final VehicleLocationData vehicleLocationData = vehicleInformationService.fetchVehicleLocation();
                return new OutputVehicleEventData(inputData, vehicleLocationData);
            }
            return new OutputVehicleEventData(inputData);
        };
    }

    @SneakyThrows
    private InputVehicleEventData getInputDataFromString(String jsonInputData) {
        return new ObjectMapper().readValue(jsonInputData, InputVehicleEventData.class);
    }
}
