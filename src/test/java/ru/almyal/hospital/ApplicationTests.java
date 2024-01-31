package ru.almyal.hospital;

import almyal.ru.hospital.soap.GenerateTimeslotRequest;
import almyal.ru.hospital.soap.TimeslotInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.util.ClassUtils;
import org.springframework.ws.client.core.WebServiceTemplate;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTests {
    private final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
    @LocalServerPort
    private int port = 0;

    @Test
    void contextLoads() {
    }

    @BeforeEach
    public void init() throws Exception {
        marshaller.setPackagesToScan(ClassUtils.getPackageName(GenerateTimeslotRequest.class));
        marshaller.afterPropertiesSet();
    }

    @Test
    void sendSOAPEnvelope() {
        WebServiceTemplate template = new WebServiceTemplate(marshaller);
        GenerateTimeslotRequest request = new GenerateTimeslotRequest();
        TimeslotInfo timeslotInfo = new TimeslotInfo();
        timeslotInfo.setDaysAhead(2);
        timeslotInfo.setStartHour(12);
        timeslotInfo.setEndHour(15);
        request.setTimeslotInfo(timeslotInfo);
        assertThat(template.marshalSendAndReceive("http://localhost:"
                + port + "/allService", request) != null);
    }
}