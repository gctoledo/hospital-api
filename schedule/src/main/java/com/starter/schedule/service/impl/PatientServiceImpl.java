package com.starter.schedule.service.impl;

import com.starter.schedule.dto.request.PatientRequest;
import com.starter.schedule.dto.response.PatientResponse;
import com.starter.schedule.entity.Patient;
import com.starter.schedule.exception.ConflictException;
import com.starter.schedule.exception.ResourceNotFoundException;
import com.starter.schedule.mapper.PatientMapper;
import com.starter.schedule.repository.PatientRepository;
import com.starter.schedule.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Override
    public PatientResponse findById(Long id) {
        var patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado"));

        return PatientMapper.toResponse(patient);
    }

    @Override
    public List<PatientResponse> findAll() {
        var patients = patientRepository.findAll();

        return patients.stream()
                .map(PatientMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public PatientResponse create(PatientRequest request) {
        if (patientRepository.existsByCpf(request.cpf())) {
            throw new ConflictException("CPF já existe");
        }

        var patient = Patient.builder()
                .name(request.name())
                .cpf(request.cpf())
                .birthDate(request.birthDate())
                .gender(request.gender())
                .phone(request.phone())
                .build();

        var savedPatient = patientRepository.save(patient);

        return PatientMapper.toResponse(savedPatient);
    }
}
