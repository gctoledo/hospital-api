package com.starter.clinic.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExamPriority {
    DEFAULT("Padrão"),
    EMERGENCY("Emergencial");

    private final String value;
}