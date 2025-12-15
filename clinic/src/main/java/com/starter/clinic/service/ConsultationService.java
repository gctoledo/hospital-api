package com.starter.clinic.service;

import com.starter.clinic.dto.request.ConsultationRequest;
import com.starter.clinic.dto.request.UpdateConsultationDateRequest;
import com.starter.clinic.dto.response.ConsultationResponse;
import com.starter.clinic.entity.Doctor;
import com.starter.clinic.entity.enums.Specialty;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ConsultationService {
    List<ConsultationResponse> findByCpf(String cpf);
    ConsultationResponse create(UUID code, ConsultationRequest request);
    ConsultationResponse updateDate(UUID code, UpdateConsultationDateRequest request);
    Doctor findAvailableDoctor(Specialty specialty, LocalDateTime dateTime);
}
