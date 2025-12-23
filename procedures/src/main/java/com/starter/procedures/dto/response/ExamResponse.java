package com.starter.procedures.dto.response;

import java.time.LocalDateTime;

public record ExamResponse(
        Long examId,
        String procedureName,
        String patientCpf,
        String status,
        LocalDateTime startDateTime
) {
}
