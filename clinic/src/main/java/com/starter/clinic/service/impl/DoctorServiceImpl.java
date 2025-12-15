package com.starter.clinic.service.impl;

import com.starter.clinic.dto.request.DoctorRequest;
import com.starter.clinic.dto.response.ConsultationResponse;
import com.starter.clinic.dto.response.DoctorResponse;
import com.starter.clinic.entity.Doctor;
import com.starter.clinic.entity.enums.ConsultationStatus;
import com.starter.clinic.entity.enums.Specialty;
import com.starter.clinic.exception.ConflictException;
import com.starter.clinic.exception.ResourceNotFoundException;
import com.starter.clinic.mapper.ConsultationMapper;
import com.starter.clinic.mapper.DoctorMapper;
import com.starter.clinic.repository.ConsultationRepository;
import com.starter.clinic.repository.DoctorRepository;
import com.starter.clinic.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;
    private final ConsultationRepository consultationRepository;
    private final ConsultationMapper consultationMapper;

    @Override
    @Transactional
    public DoctorResponse create(DoctorRequest request) {
        if (doctorRepository.existsByCrm(request.crm())) {
            throw new ConflictException("Médico com CRM " + request.crm() + " já existe");
        }

        Doctor doctor = doctorMapper.toEntity(request);
        doctor = doctorRepository.save(doctor);

        return doctorMapper.toResponse(doctor);
    }

    @Override
    @Transactional(readOnly = true)
    public DoctorResponse findById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Médico não encontrado com ID: " + id));

        return doctorMapper.toResponse(doctor);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConsultationResponse> findDoctorConsultations(Long id) {
        var doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Médico não encontrado."));

        return consultationRepository.findByDoctorId(id)
                .stream()
                .map(consultationMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public DoctorResponse update(Long id, DoctorRequest request) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Médico não encontrado com ID: " + id));

        if (!doctor.getCrm().equals(request.crm()) && doctorRepository.existsByCrm(request.crm())) {
            throw new ConflictException("Médico com CRM " + request.crm() + " já existe");
        }

        doctor.setName(request.name());
        doctor.setCrm(request.crm());
        doctor.setSpecialty(request.specialty());

        doctor = doctorRepository.save(doctor);

        return doctorMapper.toResponse(doctor);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoctorResponse> findAvailableDoctors(Specialty specialty, LocalDateTime dateTime) {
        LocalDateTime endDateTime = dateTime.plusMinutes(30);

        return doctorRepository.findAvailableDoctorBySpecialtyAndDateTime(
                specialty,
                List.of(ConsultationStatus.SCHEDULED, ConsultationStatus.RESERVED),
                dateTime,
                endDateTime
        )
                .stream()
                .map(doctorMapper::toResponse)
                .toList();
    }
}
