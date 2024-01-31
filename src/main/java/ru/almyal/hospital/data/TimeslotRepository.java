package ru.almyal.hospital.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.almyal.hospital.Timeslot;
import java.util.List;
import java.util.Optional;
@Repository
public interface TimeslotRepository extends JpaRepository<Timeslot, Long> {
    Timeslot save(Timeslot timeslot);
    Optional<Timeslot> findById(Long id);

    List<Timeslot> findAll();
    @Query(value = "SELECT * FROM timeslot WHERE doctor_id = ?1 AND EXTRACT (year FROM start_time) = ?2 " +
            "AND EXTRACT (month FROM start_time) = ?3 AND EXTRACT (day FROM start_time) = ?4 AND patient_id IS NULL",
            nativeQuery = true)
    List<Timeslot> findFreeTimeslotsByDoctorIdAndStartTime(Long id, Integer year, Integer month, Integer day);

    List<Timeslot> findTimeslotByPatientId(Long patientId);
}
