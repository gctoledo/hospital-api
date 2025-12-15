package com.starter.clinic.repository;

import com.starter.clinic.entity.Doctor;
import com.starter.clinic.entity.enums.ConsultationStatus;
import com.starter.clinic.entity.enums.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByCrm(String crm);
    boolean existsByCrm(String crm);
    List<Doctor> findBySpecialty(Specialty specialty);

    @Query("""
        SELECT d FROM Doctor d
        WHERE d.specialty = :specialty
        AND NOT EXISTS (
            SELECT 1 FROM Consultation c
            WHERE c.doctor = d
            AND c.status IN :statuses
            AND c.startDateTime < :endDateTime
            AND c.endDateTime > :startDateTime
        )
        ORDER BY d.id
        LIMIT 1
        """)
    Optional<Doctor> findAvailableDoctorBySpecialtyAndDateTime(
            @Param("specialty") Specialty specialty,
            @Param("statuses") List<ConsultationStatus> statuses,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime
    );
}
