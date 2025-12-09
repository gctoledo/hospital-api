package com.starter.schedule.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AppointmentType {
    CONSULTATION("Consulta"),
    EXAM("Exame");

    private final String value;
}