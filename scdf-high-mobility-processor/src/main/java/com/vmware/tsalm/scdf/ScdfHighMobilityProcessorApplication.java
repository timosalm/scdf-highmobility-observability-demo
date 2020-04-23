package com.vmware.tsalm.scdf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;

@Slf4j
@EnableBinding(Processor.class)
@ConfigurationPropertiesScan
@SpringBootApplication
public class ScdfHighMobilityProcessorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScdfHighMobilityProcessorApplication.class, args);
    }
}