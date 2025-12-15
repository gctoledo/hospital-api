package com.starter.schedule.dto.response;

import java.util.UUID;

public record ScheduleConsultationResponse(
        String message,
        UUID code
) {
}
