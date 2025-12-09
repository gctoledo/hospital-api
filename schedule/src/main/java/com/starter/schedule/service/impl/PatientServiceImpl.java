package com.starter.schedule.service.impl;

import com.starter.schedule.dto.request.PatientRequest;
import com.starter.schedule.dto.response.PatientResponse;
import com.starter.schedule.entity.Patient;
import com.starter.schedule.exception.ConflictException;
import com.starter.schedule.repository.PatientRepository;
import com.starter.schedule.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Override
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

        return new PatientResponse(
                savedPatient.getId(),
                savedPatient.getName(),
                savedPatient.getCpf(),
                savedPatient.getBirthDate(),
                savedPatient.getGender(),
                savedPatient.getPhone()
        );
    }
}
