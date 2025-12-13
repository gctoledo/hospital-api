package com.starter.procedures.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ProcedurePriority {
    LOW("Biaxa"),
    DEFAULT("Padrão"),
    HIGH("Alta");

    private final String value;
}