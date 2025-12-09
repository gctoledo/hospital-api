package com.starter.schedule.service;

import com.starter.schedule.dto.request.PatientRequest;
import com.starter.schedule.dto.response.PatientResponse;

public interface PatientService {
    PatientResponse create(PatientRequest request);
}
