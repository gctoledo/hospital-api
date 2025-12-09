package com.starter.schedule.dto.response;

import com.starter.schedule.entity.enums.Gender;

import java.time.LocalDate;

public record PatientResponse(
        Long id,
        String name,
        String cpf,
        LocalDate birthDate,
        Gender gender,
        String phone
) {
}
