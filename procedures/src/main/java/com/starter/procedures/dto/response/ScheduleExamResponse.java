package com.starter.procedures.dto.response;

import java.time.LocalDateTime;

public record ScheduleExamResponse(
        Long examId,
        LocalDateTime dateTime,
        String message
) {
}
