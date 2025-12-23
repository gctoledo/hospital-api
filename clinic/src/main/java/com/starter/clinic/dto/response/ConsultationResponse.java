package com.starter.clinic.dto.response;

import java.time.LocalDateTime;

public record ConsultationResponse(
        Long id,
        String doctorName,
        String patientCpf,
        String specialty,
        String status,
        LocalDateTime startDateTime
) {
}
