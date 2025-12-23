package com.starter.schedule.dto.response;

import com.starter.schedule.dto.response.external.ConsultationResponse;

public record ScheduleConsultationResponse(
        ConsultationResponse consultation,
        String message
) {
}
