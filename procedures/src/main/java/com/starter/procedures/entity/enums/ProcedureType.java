package com.starter.procedures.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProcedureType {

    BLOOD_COUNT("Hemograma Completo"),
    BLOOD_GLUCOSE("Glicemia"),
    CHOLESTEROL("Perfil Lipídico"),
    THYROID("Perfil Tireoidiano"),
    LIVER_FUNCTION("Função Hepática"),
    KIDNEY_FUNCTION("Função Renal"),
    URINALYSIS("Exame de Urina"),
    URINE_CULTURE("Urocultura"),
    X_RAY("Raio-X"),
    ULTRASOUND("Ultrassonografia"),
    ELECTROCARDIOGRAM("Eletrocardiograma"),
    ALLERGY_TEST("Teste de Alergia"),
    STOOL_TEST("Exame de Fezes"),
    PREGNANCY_TEST("Teste de Gravidez");

    private final String description;
}