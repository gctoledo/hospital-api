package com.starter.procedures.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ProcedureStatus {
    SCHEDULED("Agendado"),
    COMPLETED("Completado"),
    CANCELLED("Cancelado");

    private final String value;
}
