package ru.almyal.hospital.service;

import almyal.ru.hospital.soap.ServiceStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.almyal.hospital.Timeslot;
import ru.almyal.hospital.data.DoctorRepository;
import ru.almyal.hospital.data.TimeslotRepository;
import almyal.ru.hospital.soap.GenerateTimeslotRequest;
import almyal.ru.hospital.soap.GenerateTimeslotResponse;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Endpoint
public class TimeslotEndpoint {
    Logger log = LoggerFactory.getLogger(TimeslotEndpoint.class);
    private static final String NAMESPACE_URI = "http://ru.almyal/hospital/soap";
    private TimeslotRepository timeslotRepository;

    private DoctorRepository doctorRepository;

    @Autowired
    public TimeslotEndpoint(TimeslotRepository timeslotRepository, DoctorRepository doctorRepository) {
        this.timeslotRepository = timeslotRepository;
        this.doctorRepository = doctorRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "generateTimeslotRequest")
    @ResponsePayload
    public GenerateTimeslotResponse createTimeslot(@RequestPayload GenerateTimeslotRequest request) {
        GenerateTimeslotResponse response = new GenerateTimeslotResponse();
        ServiceStatus serviceStatus = new ServiceStatus();

        int startHour = request.getTimeslotInfo().getStartHour();
        int endHour = request.getTimeslotInfo().getEndHour();
        int daysAhead = request.getTimeslotInfo().getDaysAhead();
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(daysAhead);

        doctorRepository.findAll()
                .forEach(doctor -> startDate.datesUntil(endDate).forEach(date -> {
                    if (date.getDayOfWeek() == DayOfWeek.MONDAY ||
                            date.getDayOfWeek() == DayOfWeek.TUESDAY ||
                            date.getDayOfWeek() == DayOfWeek.WEDNESDAY ||
                            date.getDayOfWeek() == DayOfWeek.THURSDAY ||
                            date.getDayOfWeek() == DayOfWeek.FRIDAY) {
                        for (int i = startHour; i < endHour; i++) {
                            Timeslot timeslot = new Timeslot(doctor.getId(),
                                    LocalDateTime.of(date, LocalTime.of(i, 0)));
                            timeslotRepository.save(timeslot);
                        }
                    }
                }));

        serviceStatus.setStatus("SUCCESS");
        serviceStatus.setMessage("Timeslots have been generated");
        response.setServiceStatus(serviceStatus);
        return response;
    }
}