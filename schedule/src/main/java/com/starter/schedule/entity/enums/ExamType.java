package com.starter.schedule.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExamType {

    BLOOD_COUNT("Hemograma"),
    BLOOD_GLUCOSE("Glicemia"),
    CHOLESTEROL("Perfil Lipídico"),
    URINALYSIS("Exame de Urina"),
    X_RAY("Raio-X"),
    ULTRASOUND("Ultrassonografia"),
    ELECTROCARDIOGRAM("Eletrocardiograma"),
    ALLERGY_TEST("Teste de Alergia"),
    STOOL_TEST("Exame de Fezes"),
    PREGNANCY_TEST("Teste de Gravidez");

    private final String description;
}
