package com.starter.schedule.entity.enums;

public enum Gender {
    MALE("Masculino"),
    FEMALE("Feminino"),
    OTHER("Outro");

    private final String value;

    Gender(String value) {
        this.value = value;
    }
}

