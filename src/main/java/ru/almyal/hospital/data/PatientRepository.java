package ru.almyal.hospital.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.almyal.hospital.Patient;

import java.util.Optional;
@Repository
public interface PatientRepository extends CrudRepository<Patient, Long> {
    Patient save(Patient patient);

    Optional<Patient> findById(Long id);

    Iterable<Patient> findAll();
}
