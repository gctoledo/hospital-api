package com.starter.clinic.mapper;

import com.starter.clinic.dto.request.ConsultationRequest;
import com.starter.clinic.dto.response.ConsultationResponse;
import com.starter.clinic.entity.Consultation;
import org.springframework.stereotype.Component;

@Component
public class ConsultationMapper {
    public ConsultationResponse toResponse(Consultation consultation) {
        return new ConsultationResponse(
                consultation.getId(),
                consultation.getDoctor().getName(),
                consultation.getPatientCpf(),
                consultation.getSpecialty(),
                consultation.getStatus().getValue(),
                consultation.getStartDateTime(),
                consultation.getEndDateTime()
        );
    }

    public Consultation toEntity(ConsultationRequest request) {
        return Consultation.builder()
                .patientCpf(request.patientCpf())
                .specialty(request.specialty())
                .startDateTime(request.dateTime())
                .endDateTime(request.dateTime().plusMinutes(30))
                .build();
    }
}
