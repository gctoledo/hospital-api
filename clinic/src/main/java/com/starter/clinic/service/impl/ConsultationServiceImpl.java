package com.starter.clinic.service.impl;

import com.starter.clinic.dto.request.AttendConsultationRequest;
import com.starter.clinic.dto.request.ConsultationRequest;
import com.starter.clinic.dto.request.UpdateConsultationDateRequest;
import com.starter.clinic.dto.response.ConsultationResponse;
import com.starter.clinic.dto.response.DiagnoseResult;
import com.starter.clinic.entity.Consultation;
import com.starter.clinic.entity.Doctor;
import com.starter.clinic.entity.enums.ConsultationStatus;
import com.starter.clinic.entity.enums.Specialty;
import com.starter.clinic.exception.ConflictException;
import com.starter.clinic.exception.ResourceNotFoundException;
import com.starter.clinic.exception.UnauthorizedException;
import com.starter.clinic.mapper.ConsultationMapper;
import com.starter.clinic.repository.ConsultationRepository;
import com.starter.clinic.repository.DoctorRepository;
import com.starter.clinic.service.ConsultationService;
import com.starter.clinic.service.DiagnoseService;
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
    private final DiagnoseService diagnoseService;

    @Transactional(readOnly = true)
    @Override
    public List<ConsultationResponse> findByCpf(String cpf) {
        return consultationRepository.findByPatientCpf(cpf)
                .stream()
                .map(consultationMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<ConsultationResponse> findAll(Specialty specialty) {
        if (specialty == null) {
            return consultationRepository.findAll()
                    .stream()
                    .map(consultationMapper::toResponse)
                    .toList();
        }

        return consultationRepository.findBySpecialty(specialty)
                .stream()
                .map(consultationMapper::toResponse)
                .toList();
    }

    @Transactional
    @Override
    public ConsultationResponse makeReserve(ConsultationRequest request) {
        List<Doctor> availableDoctors = getAvailableDoctors(request.specialty(), request.dateTime());

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

        if (consultation.getStatus() == ConsultationStatus.COMPLETED || consultation.getStatus() == ConsultationStatus.CANCELLED) {
            throw new ConflictException("Consulta foi cancelada ou já foi concluída.");
        }

        List<Doctor> availableDoctors = getAvailableDoctors(consultation.getSpecialty(), request.dateTime());

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

    @Transactional
    @Override
    public DiagnoseResult attend(Long id, AttendConsultationRequest request) {
        var consultation = consultationRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consulta não encontrada."));

        if (consultation.getStatus().equals(ConsultationStatus.COMPLETED) || consultation.getStatus().equals(ConsultationStatus.CANCELLED)) {
            throw new ConflictException("Consulta foi cancelada ou já foi concluída.");
        }

        if (!consultation.getPatientCpf().equals(request.patientCpf())) {
            throw new UnauthorizedException("Consulta não pertence ao CPF fornecido.");
        }

        DiagnoseResult diagnose = diagnoseService.execute(request.patientCpf(), request.symptoms());

        consultation.setStatus(ConsultationStatus.COMPLETED);
        consultationRepository.save(consultation);

        return diagnose;
    }

    private List<Doctor> getAvailableDoctors(Specialty specialty, LocalDateTime dateTime) {
        return doctorRepository.findAvailableDoctorBySpecialtyAndDateTime(
                specialty,
                List.of(ConsultationStatus.SCHEDULED, ConsultationStatus.RESERVED),
                dateTime,
                dateTime.plusMinutes(30)
        );
    }
}
