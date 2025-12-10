package com.starter.schedule.mapper;

import com.starter.schedule.dto.response.PatientResponse;
import com.starter.schedule.entity.Patient;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {

    public PatientResponse toResponse(Patient patient) {
        return new PatientResponse(
                patient.getId(),
                patient.getName(),
                patient.getCpf(),
                patient.getBirthDate(),
                patient.getGender(),
                patient.getPhone()
        );
    }
}
