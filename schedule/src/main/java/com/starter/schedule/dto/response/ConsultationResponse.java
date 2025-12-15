package com.starter.schedule.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record ConsultationResponse(
        UUID code,
        String doctorName,
        String patientCpf,
        String specialty,
        String status,
        LocalDateTime dateTime
) {
}
