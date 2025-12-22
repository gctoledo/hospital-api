package com.starter.schedule.service;

import com.starter.schedule.dto.request.ScheduleConsultationRequest;
import com.starter.schedule.dto.request.ScheduleExamRequest;
import com.starter.schedule.dto.request.external.UpdateScheduleDateRequest;
import com.starter.schedule.dto.response.ScheduleConsultationResponse;
import com.starter.schedule.dto.response.ScheduleExamResponse;
import com.starter.schedule.dto.response.external.ConsultationResponse;

import java.util.List;

public interface ScheduleService {
    List<ConsultationResponse> findConsultationsByCpf(String cpf);
    ScheduleConsultationResponse createConsultation(ScheduleConsultationRequest request);
    ScheduleExamResponse createExam(ScheduleExamRequest request);
    ScheduleConsultationResponse updateConsultationDate(Long id, UpdateScheduleDateRequest request);
}
