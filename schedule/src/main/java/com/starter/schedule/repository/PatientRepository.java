package com.starter.schedule.repository;

import com.starter.schedule.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByCpf(String cpf);

    boolean existsByCpf(String cpf);
}
