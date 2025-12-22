package com.starter.procedures.service;

import com.starter.procedures.dto.request.ExamRequest;
import com.starter.procedures.dto.request.ReserveExamRequest;
import com.starter.procedures.dto.request.ScheduleExamRequest;
import com.starter.procedures.dto.response.ExamResponse;
import com.starter.procedures.dto.response.ReserveExamResponse;
import com.starter.procedures.dto.response.ScheduleExamResponse;

public interface ExamService {
    ReserveExamResponse makeReserve(ReserveExamRequest request);
    ExamResponse create(ExamRequest request);
    ScheduleExamResponse schedule(Long examId, ScheduleExamRequest request);
}
