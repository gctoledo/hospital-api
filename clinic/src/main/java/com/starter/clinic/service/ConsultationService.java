package com.starter.clinic.service;

import com.starter.clinic.dto.request.ConsultationRequest;
import com.starter.clinic.dto.response.ConsultationResponse;

import java.util.List;

public interface ConsultationService {
    List<ConsultationResponse> findByCpf(String cpf);
    ConsultationResponse create(ConsultationRequest request);
}
