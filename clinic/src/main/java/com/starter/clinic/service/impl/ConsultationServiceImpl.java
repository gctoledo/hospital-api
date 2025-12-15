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
import java.util.UUID;

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
    public ConsultationResponse create(UUID code, ConsultationRequest request) {
        Doctor availableDoctor = findAvailableDoctor(request.specialty(), request.dateTime());

        var consultation = consultationMapper.toEntity(request);
        consultation.setCode(code);
        consultation.setDoctor(availableDoctor);

        Consultation savedConsultation = consultationRepository.save(consultation);

        return consultationMapper.toResponse(savedConsultation);
    }

    @Transactional
    @Override
    public ConsultationResponse updateDate(UUID code, UpdateConsultationDateRequest request) {
        var consultation = consultationRepository
                .findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Consulta não encontrada."));

        Doctor availableDoctor = findAvailableDoctor(consultation.getSpecialty(), request.dateTime());

        consultation.setDoctor(availableDoctor);
        consultation.setDateTime(request.dateTime());
        consultation.setStatus(ConsultationStatus.SCHEDULED);

        Consultation savedConsultation = consultationRepository.save(consultation);

        return consultationMapper.toResponse(savedConsultation);
    }

    @Transactional(readOnly = true)
    public Doctor findAvailableDoctor(Specialty specialty, LocalDateTime dateTime) {
        return doctorRepository.findBySpecialty(specialty)
                .stream()
                .filter(doctor -> !consultationRepository.existsByDoctorIdAndDateTimeAndStatusNot(doctor.getId(), dateTime, ConsultationStatus.CANCELLED))
                .findFirst()
                .orElseThrow(() -> new ConflictException(
                        String.format(
                                "Nenhum médico da especialidade %s disponível.",
                                specialty.getValue()
                        )
                ));
    }
}
