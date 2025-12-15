package com.starter.schedule.service;

import com.starter.schedule.dto.request.ScheduleConsultationRequest;
import com.starter.schedule.dto.request.UpdateScheduleDateRequest;
import com.starter.schedule.dto.response.ConsultationResponse;
import com.starter.schedule.dto.response.ScheduleConsultationResponse;

import java.util.List;
import java.util.UUID;

public interface ScheduleService {
    List<ConsultationResponse> findConsultationsByCpf(String cpf);
    ScheduleConsultationResponse createConsultation(ScheduleConsultationRequest request);
    ScheduleConsultationResponse updateConsultationDate(UUID id, UpdateScheduleDateRequest request);
}
