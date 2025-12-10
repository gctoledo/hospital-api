package com.starter.clinic.service.impl;

import com.starter.clinic.dto.request.DoctorRequest;
import com.starter.clinic.dto.response.DoctorResponse;
import com.starter.clinic.entity.Doctor;
import com.starter.clinic.entity.enums.Specialty;
import com.starter.clinic.exception.ConflictException;
import com.starter.clinic.exception.ResourceNotFoundException;
import com.starter.clinic.mapper.DoctorMapper;
import com.starter.clinic.repository.DoctorRepository;
import com.starter.clinic.service.DoctorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    @Transactional
    public DoctorResponse create(DoctorRequest request) {
        log.info("Criando médico com CRM: {}", request.crm());

        if (doctorRepository.existsByCrm(request.crm())) {
            throw new ConflictException("Médico com CRM " + request.crm() + " já existe");
        }

        Doctor doctor = doctorMapper.toEntity(request);
        doctor = doctorRepository.save(doctor);

        log.info("Médico criado com ID: {}", doctor.getId());
        return doctorMapper.toResponse(doctor);
    }

    @Transactional(readOnly = true)
    public DoctorResponse findById(Long id) {
        log.info("Buscando médico por ID: {}", id);

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Médico não encontrado com ID: " + id));
        return doctorMapper.toResponse(doctor);
    }

    @Transactional(readOnly = true)
    public DoctorResponse findByCrm(String crm) {
        log.info("Buscando médico por CRM: {}", crm);

        Doctor doctor = doctorRepository.findByCrm(crm)
                .orElseThrow(() -> new ResourceNotFoundException("Médico não encontrado com CRM: " + crm));

        return doctorMapper.toResponse(doctor);
    }

    @Transactional(readOnly = true)
    public List<DoctorResponse> findAll() {
        log.info("Buscando todos os médicos");

        return doctorRepository.findAll().stream()
                .map(doctorMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<DoctorResponse> findBySpecialty(Specialty specialty) {
        log.info("Buscando médicos por especialidade: {}", specialty);

        return doctorRepository.findBySpecialtyAndActiveTrue(specialty).stream()
                .map(doctorMapper::toResponse)
                .toList();
    }

    @Transactional
    public DoctorResponse update(Long id, DoctorRequest request) {
        log.info("Atualizando médico com ID: {}", id);

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Médico não encontrado com ID: " + id));

        if (!doctor.getCrm().equals(request.crm()) && doctorRepository.existsByCrm(request.crm())) {
            throw new ConflictException("Médico com CRM " + request.crm() + " já existe");
        }

        doctor.setName(request.name());
        doctor.setCrm(request.crm());
        doctor.setSpecialty(request.specialty());

        doctor = doctorRepository.save(doctor);

        log.info("Médico atualizado com ID: {}", doctor.getId());
        return doctorMapper.toResponse(doctor);
    }

    @Transactional
    public void deactivate(Long id) {
        log.info("Desativando médico com ID: {}", id);

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Médico não encontrado com ID: " + id));
        doctor.setActive(false);
        doctorRepository.save(doctor);

        log.info("Médico desativado com ID: {}", id);
    }

    @Transactional
    public void activate(Long id) {
        log.info("Ativando médico com ID: {}", id);

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Médico não encontrado com ID: " + id));
        doctor.setActive(true);
        doctorRepository.save(doctor);

        log.info("Médico ativado com ID: {}", id);
    }
}
