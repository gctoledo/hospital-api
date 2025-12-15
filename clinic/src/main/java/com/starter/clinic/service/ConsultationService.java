package com.starter.clinic.service;

import com.starter.clinic.dto.request.ConsultationRequest;
import com.starter.clinic.dto.request.UpdateConsultationDateRequest;
import com.starter.clinic.dto.response.ConsultationResponse;
import com.starter.clinic.entity.enums.Specialty;

import java.util.List;

public interface ConsultationService {
    List<ConsultationResponse> findByCpf(String cpf);
    List<ConsultationResponse> findAll(Specialty specialty);
    ConsultationResponse makeReserve(ConsultationRequest request);
    ConsultationResponse create(Long id);
    ConsultationResponse updateDate(Long id, UpdateConsultationDateRequest request);
}
