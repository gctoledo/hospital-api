package com.starter.clinic.service;

import com.starter.clinic.dto.request.DoctorRequest;
import com.starter.clinic.dto.response.DoctorResponse;
import com.starter.clinic.entity.Doctor;
import com.starter.clinic.entity.enums.Specialty;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DoctorService {
    DoctorResponse create(DoctorRequest request);
    DoctorResponse findById(Long id);
    DoctorResponse findByCrm(String crm);
    List<DoctorResponse> findAll();
    List<DoctorResponse> findBySpecialty(Specialty specialty);
    DoctorResponse update(Long id, DoctorRequest request);
    Optional<Doctor> findAvailableDoctor(Specialty specialty, LocalDateTime dateTime);
}
