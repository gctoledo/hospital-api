package com.starter.schedule.dto.response;

import java.time.LocalDateTime;

public record ScheduleExamResponse(
        Long id,
        String procedureName,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        String message
) {
}
