package com.starter.schedule.service;

import com.starter.schedule.dto.request.ScheduleConsultationRequest;
import com.starter.schedule.dto.request.ScheduleExamRequest;
import com.starter.schedule.dto.request.external.UpdateDateRequest;
import com.starter.schedule.dto.response.ScheduleConsultationResponse;
import com.starter.schedule.dto.response.ScheduleExamResponse;
import com.starter.schedule.dto.response.external.ConsultationResponse;
import com.starter.schedule.dto.response.external.ExamResponse;

import java.util.List;

public interface ScheduleService {
    List<ConsultationResponse> findConsultationsByCpf(String cpf);
    List<ExamResponse> findExamsByCpf(String cpf);
    ScheduleConsultationResponse scheduleConsultation(ScheduleConsultationRequest request);
    ScheduleExamResponse scheduleExam(ScheduleExamRequest request);
    ScheduleConsultationResponse updateConsultationDate(Long id, UpdateDateRequest request);
    ScheduleExamResponse updateExamDate(Long id, UpdateDateRequest request);
}
