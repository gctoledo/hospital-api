package com.starter.clinic.messaging.event;

import com.starter.clinic.entity.enums.Specialty;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateConsultationEvent(
        UUID code,
        Specialty specialty,
        String patientCpf,
        LocalDateTime dateTime
) {
}
