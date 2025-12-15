package com.starter.clinic.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ConsultationStatus {
    RESERVED("Reservado"),
    SCHEDULED("Agendado"),
    COMPLETED("Completado"),
    CANCELLED("Cancelado");

    private final String value;
}
