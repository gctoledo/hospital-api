package com.starter.procedures.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Complexity {
    DEFAULT("Padrão"),
    HIGH("Alta complexidade");

    private final String value;
}