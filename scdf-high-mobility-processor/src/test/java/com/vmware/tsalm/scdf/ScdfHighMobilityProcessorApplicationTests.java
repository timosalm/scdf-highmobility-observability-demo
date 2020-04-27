package com.vmware.tsalm.scdf;

import com.vmware.tsalm.scdf.infrastructure.service.model.HighMobilityConfigurationProperties;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class ScdfHighMobilityProcessorApplicationTests {

    @MockBean
    private HighMobilityConfigurationProperties highMobilityConfigurationPropertiesValidationsMock;

    @Test
    void contextLoads() {
    }
}
