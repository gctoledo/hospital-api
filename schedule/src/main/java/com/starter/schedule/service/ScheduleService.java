package com.starter.schedule.service;

import com.starter.schedule.dto.request.ScheduleConsultationRequest;
import com.starter.schedule.dto.response.ConsultationResponse;
import com.starter.schedule.dto.response.ScheduleConsultationResponse;

import java.util.List;

public interface ScheduleService {
    List<ConsultationResponse> findConsultationsByCpf(String cpf);
    ScheduleConsultationResponse createConsultation(ScheduleConsultationRequest request);
}
