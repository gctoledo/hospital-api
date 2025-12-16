package com.starter.procedures.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Complexity {
    LOW("Baixo"),
    MEDIUM("Médio"),
    HIGH("Alto");

    private final String value;
}