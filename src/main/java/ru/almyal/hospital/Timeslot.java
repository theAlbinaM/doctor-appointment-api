package ru.almyal.hospital;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@RequiredArgsConstructor
public class Timeslot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long doctorId;

    private Long patientId;

    private LocalDateTime startTime;
    public Timeslot(Long doctorId, LocalDateTime startTime) {
        this.doctorId = doctorId;
        this.startTime =startTime;
    }
}