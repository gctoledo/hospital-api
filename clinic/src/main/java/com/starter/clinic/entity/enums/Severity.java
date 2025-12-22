package com.starter.clinic.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Severity {

    LOW(2, "Baixa"),
    DEFAULT(5, "Padrão"),
    HIGH(8, "Alta"),
    EMERGENCY(10, "Emergência");

    private final int score;
    private final String description;
}
