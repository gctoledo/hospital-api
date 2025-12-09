package com.starter.schedule.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AppointmentStatus {
    PENDING("Pendente"),
    CONFIRMED("Confirmado"),
    REJECTED("Rejeitado"),
    CANCELLED("Cancelado");

    private final String value;
}
