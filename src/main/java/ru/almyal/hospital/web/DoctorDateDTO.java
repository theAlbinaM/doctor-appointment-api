package ru.almyal.hospital.web;

import lombok.Data;

@Data
public class DoctorDateDTO {
    private Long doctorId;
    private Integer year;
    private Integer month;
    private Integer day;
}
