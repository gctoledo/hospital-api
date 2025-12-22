package com.starter.schedule.dto.response.external;

import java.time.LocalDateTime;

public record CreateExamResponse(
        Long examId,
        String procedureName,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime
) {
}
