package com.starter.clinic.repository;

import com.starter.clinic.entity.Consultation;
import com.starter.clinic.entity.enums.ConsultationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    boolean existsByDoctorIdAndDateTimeAndStatusNot(Long doctorId, LocalDateTime dateTime, ConsultationStatus status);
    List<Consultation> findByPatientCpf(String patientCpf);
    Optional<Consultation> findByCode(UUID code);
}
