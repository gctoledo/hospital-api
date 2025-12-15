package com.starter.schedule.messaging.event;

import com.starter.schedule.entity.enums.Specialty;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateConsultationEvent(
        UUID code,
        String patientCpf,
        Specialty specialty,
        LocalDateTime dateTime
) {
}
