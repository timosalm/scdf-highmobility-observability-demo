package com.vmware.tsalm.scdf.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmware.tsalm.scdf.domain.model.VehicleLocationData;
import com.vmware.tsalm.scdf.domain.model.service.VehicleInformationService;
import com.vmware.tsalm.scdf.interfaces.model.InputVehicleEventData;
import com.vmware.tsalm.scdf.interfaces.model.InputVehicleEventData.VehicleBaseData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.support.MessageBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static com.vmware.tsalm.scdf.interfaces.model.InputVehicleEventData.VehicleEvent;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class VehicleEventProcessorTest {

    public static final VehicleBaseData VEHICLE = new VehicleBaseData("1HMD11338H4E954D9", "835FFBE1CD76149098");

    @Autowired
    private Processor processor;

    @Autowired
    private MessageCollector messageCollector;
    
    @MockBean
    private VehicleInformationService vehicleInformationServiceMock;

    private SimpleDateFormat format;

    @BeforeEach
    public void init() {
        format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    @Test
    void testProcessingOfVehicleLocationChangedEvent() throws JsonProcessingException {
        final VehicleLocationData locationData = new VehicleLocationData(new Date(), 1.1, 1.2);
        when(vehicleInformationServiceMock.fetchVehicleLocation()).thenReturn(locationData);

        final VehicleEvent event = new VehicleEvent("vehicle_location_changed", new Date());
        final InputVehicleEventData eventData = new InputVehicleEventData(VEHICLE, event);
        final String eventDataJsonString = new ObjectMapper().writeValueAsString(eventData);
        processor.input().send(MessageBuilder.withPayload(eventDataJsonString).build());

        final Object payload = messageCollector.forChannel(processor.output()).poll().getPayload();

        assertEquals("{\"eventType\":\""
                + event.getType() + "\",\"receivedAt\":\""
                + format.format(event.getReceivedAt()) + "\",\"vin\":\""
                + VEHICLE.getVin() + "\",\"serialNumber\":\""
                + VEHICLE.getSerialNumber() + "\",\"location\":{\"createdAt\":\""
                + format.format(locationData.getCreatedAt()) + "\",\"latitude\":"
                + locationData.getLatitude() + ",\"longitude\":"
                + locationData.getLongitude() + "}}", payload.toString());
    }

    @Test
    void testProcessingOfOtherEvents() throws JsonProcessingException {
        final VehicleEvent event = new VehicleEvent("other_event", new Date());
        final InputVehicleEventData eventData = new InputVehicleEventData(VEHICLE, event);
        final String eventDataJsonString = new ObjectMapper().writeValueAsString(eventData);
        processor.input().send(MessageBuilder.withPayload(eventDataJsonString).build());

        final Object payload = messageCollector.forChannel(processor.output()).poll().getPayload();

        assertEquals("{\"eventType\":\""
                + event.getType() + "\",\"receivedAt\":\""
                + format.format(event.getReceivedAt()) + "\",\"vin\":\""
                + VEHICLE.getVin() + "\",\"serialNumber\":\""
                + VEHICLE.getSerialNumber() + "\"}", payload.toString());
    }
}