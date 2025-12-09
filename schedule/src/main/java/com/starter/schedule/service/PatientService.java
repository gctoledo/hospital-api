package com.starter.schedule.service;

import com.starter.schedule.dto.request.PatientRequest;
import com.starter.schedule.dto.response.PatientResponse;

import java.util.List;

public interface PatientService {
    PatientResponse findById(Long id);
    List<PatientResponse> findAll();
    PatientResponse create(PatientRequest request);
    PatientResponse update(Long id, PatientRequest request);
}
