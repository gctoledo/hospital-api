package com.starter.procedures.service;

import com.starter.procedures.dto.request.ExamRequest;
import com.starter.procedures.dto.request.ReserveExamRequest;
import com.starter.procedures.dto.request.ScheduleExamRequest;
import com.starter.procedures.dto.request.UpdateExamDateRequest;
import com.starter.procedures.dto.response.ExamResponse;
import com.starter.procedures.dto.response.ReserveExamResponse;
import com.starter.procedures.dto.response.ScheduleExamResponse;

import java.util.List;

public interface ExamService {
    List<ExamResponse> findByCpf(String cpf);
    ReserveExamResponse makeReserve(ReserveExamRequest request);
    ExamResponse create(ExamRequest request);
    ScheduleExamResponse confirmDate(Long examId, ScheduleExamRequest request);
    ExamResponse updateDate(Long examId, UpdateExamDateRequest request);
}
