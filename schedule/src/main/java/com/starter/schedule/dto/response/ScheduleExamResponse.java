package com.starter.schedule.dto.response;

import com.starter.schedule.dto.response.external.ExamResponse;

public record ScheduleExamResponse(
        ExamResponse exam,
        String message
) {
}
