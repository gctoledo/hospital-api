package com.starter.clinic.dto.response;

import com.starter.clinic.entity.enums.Specialty;

public record DoctorResponse(
        Long id,
        String name,
        String crm,
        Specialty specialty,
        Boolean active
) {
}
