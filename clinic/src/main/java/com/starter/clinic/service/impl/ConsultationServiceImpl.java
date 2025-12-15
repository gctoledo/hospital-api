package com.starter.clinic.service.impl;

import com.starter.clinic.dto.request.ConsultationRequest;
import com.starter.clinic.dto.request.UpdateConsultationDateRequest;
import com.starter.clinic.dto.response.ConsultationResponse;
import com.starter.clinic.entity.Consultation;
import com.starter.clinic.entity.Doctor;
import com.starter.clinic.entity.enums.ConsultationStatus;
import com.starter.clinic.exception.ConflictException;
import com.starter.clinic.exception.ResourceNotFoundException;
import com.starter.clinic.mapper.ConsultationMapper;
import com.starter.clinic.repository.ConsultationRepository;
import com.starter.clinic.service.ConsultationService;
import com.starter.clinic.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultationServiceImpl implements ConsultationService {

    private final DoctorService doctorService;
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
    public ConsultationResponse makeReserve(ConsultationRequest request) {
        List<Doctor> availableDoctors = doctorService.getAvailableDoctors(request.specialty(), request.dateTime());

        if (availableDoctors.isEmpty()) {
            throw new ConflictException(
                    String.format(
                            "Nenhum médico da especialidade %s disponível.",
                            request.specialty().getValue()
                    )
            );
        }

        Doctor availableDoctor = availableDoctors.getFirst();

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

        List<Doctor> availableDoctors = doctorService.getAvailableDoctors(consultation.getSpecialty(), request.dateTime());

        if (availableDoctors.isEmpty()) {
            throw new ConflictException(
                    String.format(
                            "Nenhum médico da especialidade %s disponível.",
                            consultation.getSpecialty().getValue()
                    )
            );
        }

        Doctor availableDoctor = availableDoctors.getFirst();

        consultation.setDoctor(availableDoctor);
        consultation.setStartDateTime(request.dateTime());
        consultation.setEndDateTime(request.dateTime().plusMinutes(30));
        consultation.setStatus(ConsultationStatus.SCHEDULED);

        Consultation savedConsultation = consultationRepository.save(consultation);

        return consultationMapper.toResponse(savedConsultation);
    }
}
