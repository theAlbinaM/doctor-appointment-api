package ru.almyal.hospital.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.almyal.hospital.Timeslot;
import ru.almyal.hospital.data.PatientRepository;
import ru.almyal.hospital.data.TimeslotRepository;
import java.util.List;

@RestController
@RequestMapping("/timeslots")
public class TimeslotServiceController {
    @Autowired
    private TimeslotRepository timeslotRepository;
    @Autowired
    private PatientRepository patientRepository;
    @RequestMapping(value = "/timeslots")
    public ResponseEntity<Object> getTimeslots() {
        return new ResponseEntity<>(timeslotRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping(value = "/search", consumes = "application/json")
    public ResponseEntity<Object> getFreeTimeslotsByDoctorByDate(@RequestBody DoctorDateDTO dto) {
        return new ResponseEntity<>(timeslotRepository.findFreeTimeslotsByDoctorIdAndStartTime(dto.getDoctorId(),
                dto.getYear(), dto.getMonth(), dto.getDay()),
                HttpStatus.OK);
    }



    @PatchMapping(value = "/choose/{timeslot_id}")
    public ResponseEntity<Object> chooseTimeslotById(@RequestBody Long patientId,
                                                     @PathVariable(name = "timeslot_id") Long timeslotId) {
        Timeslot timeslot = timeslotRepository.findById(timeslotId).orElse(null);
        if (patientRepository.findById(patientId).isEmpty()){
            return new ResponseEntity<>("Incorrect patient id", HttpStatus.OK);
        }
        if (timeslot == null) return new ResponseEntity<>("Incorrect timeslot id", HttpStatus.NOT_FOUND);
        if (timeslot.getPatientId() == null) {
            timeslot.setPatientId(patientId);
        }
        return new ResponseEntity<>(timeslotRepository.save(timeslot), HttpStatus.OK);
    }

    @GetMapping(path = "/{patientId}")
    public List<Timeslot> findTimeslotByPatientId(@PathVariable(name = "patientId") Long patientId) {
        return timeslotRepository.findTimeslotByPatientId(patientId);
    }
}