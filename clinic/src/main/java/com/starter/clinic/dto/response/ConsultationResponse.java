package com.starter.clinic.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record ConsultationResponse(
        Long id,
        UUID code,
        String doctorName,
        String patientCpf,
        String specialty,
        String status,
        LocalDateTime dateTime
) {
}
