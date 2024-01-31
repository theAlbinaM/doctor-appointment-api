package ru.almyal.hospital.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ru.almyal.hospital.Doctor;

import java.util.List;
import java.util.Optional;

@Component
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Doctor save(Doctor doctor);

    Optional<Doctor> findById(Long id);

    List<Doctor> findAll();
}
