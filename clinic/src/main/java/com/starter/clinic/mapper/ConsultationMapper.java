package com.starter.clinic.mapper;

import com.starter.clinic.dto.request.ConsultationRequest;
import com.starter.clinic.dto.response.ConsultationResponse;
import com.starter.clinic.entity.Consultation;
import com.starter.clinic.entity.enums.ConsultationStatus;
import org.springframework.stereotype.Component;

@Component
public class ConsultationMapper {
    public ConsultationResponse toResponse(Consultation consultation) {
        return new ConsultationResponse(
                consultation.getId(),
                consultation.getPatientCpf(),
                consultation.getSpecialty().getValue(),
                consultation.getStatus().getValue(),
                consultation.getDateTime()
        );
    }

    public Consultation toEntity(ConsultationRequest request) {
        return Consultation.builder()
                .patientCpf(request.patientCpf())
                .specialty(request.specialty())
                .dateTime(request.dateTime())
                .status(ConsultationStatus.SCHEDULED)
                .build();
    }
}
