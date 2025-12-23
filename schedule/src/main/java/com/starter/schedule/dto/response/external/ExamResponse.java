package com.starter.schedule.dto.response.external;

import java.time.LocalDateTime;

public record ExamResponse(
        Long examId,
        String procedureName,
        String patientCpf,
        String status,
        LocalDateTime startDateTime
) {
}
