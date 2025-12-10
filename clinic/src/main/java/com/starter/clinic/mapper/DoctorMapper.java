package com.starter.clinic.mapper;

import com.starter.clinic.dto.request.DoctorRequest;
import com.starter.clinic.dto.response.DoctorResponse;
import com.starter.clinic.entity.Doctor;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper {
    public DoctorResponse toResponse(Doctor doctor) {
        return new DoctorResponse(
            doctor.getId(),
            doctor.getName(),
            doctor.getCrm(),
            doctor.getSpecialty(),
            doctor.isActive()
        );
    }

    public Doctor toEntity(DoctorRequest request) {
        return Doctor.builder()
                .name(request.name())
                .crm(request.crm())
                .specialty(request.specialty())
                .active(true)
                .build();
    }
}
