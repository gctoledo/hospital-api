package com.starter.clinic.service;

import com.starter.clinic.dto.request.DoctorRequest;
import com.starter.clinic.dto.response.DoctorResponse;
import com.starter.clinic.entity.Doctor;
import com.starter.clinic.entity.enums.Specialty;

import java.time.LocalDateTime;
import java.util.List;

public interface DoctorService {
    DoctorResponse create(DoctorRequest request);
    DoctorResponse findById(Long id);
    DoctorResponse findByCrm(String crm);
    DoctorResponse update(Long id, DoctorRequest request);
    List<DoctorResponse> findAvailableDoctors(Specialty specialty, LocalDateTime dateTime);
    List<Doctor> getAvailableDoctors(Specialty specialty, LocalDateTime dateTime);
}
