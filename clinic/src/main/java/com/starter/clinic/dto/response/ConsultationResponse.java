package com.starter.clinic.dto.response;

import com.starter.clinic.entity.enums.Specialty;

import java.time.LocalDateTime;

public record ConsultationResponse(
        Long id,
        String doctorName,
        String patientCpf,
        Specialty specialty,
        String status,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime
) {
}
