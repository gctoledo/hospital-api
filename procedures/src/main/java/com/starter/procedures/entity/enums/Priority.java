package com.starter.procedures.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Priority {
    DEFAULT("Padrão"),
    EMERGENCY("Emergencial");

    private final String value;
}