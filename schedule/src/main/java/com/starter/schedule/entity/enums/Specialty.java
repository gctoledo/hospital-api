package com.starter.schedule.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Specialty {
    GENERAL_PRACTICE("Clínica Geral"),
    CARDIOLOGY("Cardiologia"),
    DERMATOLOGY("Dermatologia"),
    ORTHOPEDICS("Ortopedia"),
    NEUROLOGY("Neurologia"),
    PEDIATRICS("Pediatria"),
    GYNECOLOGY("Ginecologia"),
    OPHTHALMOLOGY("Oftalmologia"),
    PSYCHIATRY("Psiquiatria"),
    UROLOGY("Urologia"),
    PULMONOLOGY("Pneumologia"),
    ENDOCRINOLOGY("Endocrinologia"),
    ONCOLOGY("Oncologia"),
    RHEUMATOLOGY("Reumatologia");

    private final String value;
}