package com.starter.procedures.mapper;

import com.starter.procedures.dto.request.ExamRequest;
import com.starter.procedures.dto.request.ReserveExamRequest;
import com.starter.procedures.dto.response.ExamResponse;
import com.starter.procedures.dto.response.ReserveExamResponse;
import com.starter.procedures.entity.Exam;
import com.starter.procedures.entity.Procedure;
import com.starter.procedures.entity.enums.ExamStatus;
import com.starter.procedures.entity.enums.Priority;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ExamMapper {

    public ReserveExamResponse toReserveResponse(Exam exam, Procedure procedure){
        return new ReserveExamResponse(
                exam.getId(),
                procedure.getName()
        );
    }
    public ExamResponse toResponse(Exam exam) {
        return new ExamResponse(
                exam.getId(),
                exam.getProcedure().getName(),
                exam.getStartDateTime(),
                exam.getEndDateTime()
        );
    }

    public Exam toEntity(ReserveExamRequest request, Procedure procedure) {
        return Exam.builder()
                .patientCpf(request.patientCpf())
                .priority(request.priority())
                .procedure(procedure)
                .status(ExamStatus.CREATED)
                .build();
    }

    public Exam toEntity(ExamRequest request, Procedure procedure) {
        LocalDateTime startDateTime = request.dateTime();
        LocalDateTime endDateTime = startDateTime.plusMinutes(procedure.getDurationInMinutes());

        return Exam.builder()
                .patientCpf(request.patientCpf())
                .procedure(procedure)
                .priority(Priority.DEFAULT)
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .status(ExamStatus.SCHEDULED)
                .build();
    }
}
