package com.starter.clinic.service.impl;

import com.starter.clinic.dto.request.ConsultationRequest;
import com.starter.clinic.dto.request.UpdateConsultationDateRequest;
import com.starter.clinic.dto.response.ConsultationResponse;
import com.starter.clinic.entity.Consultation;
import com.starter.clinic.entity.Doctor;
import com.starter.clinic.entity.enums.ConsultationStatus;
import com.starter.clinic.entity.enums.Specialty;
import com.starter.clinic.exception.ConflictException;
import com.starter.clinic.exception.ResourceNotFoundException;
import com.starter.clinic.mapper.ConsultationMapper;
import com.starter.clinic.repository.ConsultationRepository;
import com.starter.clinic.repository.DoctorRepository;
import com.starter.clinic.service.ConsultationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultationServiceImpl implements ConsultationService {

    private final DoctorRepository doctorRepository;
    private final ConsultationRepository consultationRepository;
    private final ConsultationMapper consultationMapper;

    @Transactional(readOnly = true)
    @Override
    public List<ConsultationResponse> findByCpf(String cpf) {
        return consultationRepository.findByPatientCpf(cpf)
                .stream()
                .map(consultationMapper::toResponse)
                .toList();
    }

    @Transactional
    @Override
    public ConsultationResponse makeReservation(ConsultationRequest request) {
        Doctor availableDoctor = findAvailableDoctor(request.specialty(), request.dateTime());

        var consultation = consultationMapper.toEntity(request);
        consultation.setDoctor(availableDoctor);
        consultation.setStatus(ConsultationStatus.RESERVED);

        Consultation savedConsultation = consultationRepository.save(consultation);

        return consultationMapper.toResponse(savedConsultation);
    }

    @Transactional
    @Override
    public ConsultationResponse create(Long id) {
        var consultation = consultationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consulta não encontrada"));

        consultation.setStatus(ConsultationStatus.SCHEDULED);

        Consultation savedConsultation = consultationRepository.save(consultation);

        return consultationMapper.toResponse(savedConsultation);
    }

    @Transactional
    @Override
    public ConsultationResponse updateDate(Long id, UpdateConsultationDateRequest request) {
        var consultation = consultationRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consulta não encontrada."));

        Doctor availableDoctor = findAvailableDoctor(consultation.getSpecialty(), request.dateTime());

        consultation.setDoctor(availableDoctor);
        consultation.setStartDateTime(request.dateTime());
        consultation.setEndDateTime(request.dateTime().plusMinutes(30));
        consultation.setStatus(ConsultationStatus.SCHEDULED);

        Consultation savedConsultation = consultationRepository.save(consultation);

        return consultationMapper.toResponse(savedConsultation);
    }

    @Transactional(readOnly = true)
    private Doctor findAvailableDoctor(Specialty specialty, LocalDateTime dateTime) {
        LocalDateTime endDateTime = dateTime.plusMinutes(30);

        return doctorRepository.findAvailableDoctorBySpecialtyAndDateTime(
                specialty,
                List.of(ConsultationStatus.SCHEDULED, ConsultationStatus.RESERVED),
                dateTime,
                endDateTime
        ).orElseThrow(() -> new ConflictException(
                String.format(
                        "Nenhum médico da especialidade %s disponível.",
                        specialty.getValue()
                )
        ));
    }
}
