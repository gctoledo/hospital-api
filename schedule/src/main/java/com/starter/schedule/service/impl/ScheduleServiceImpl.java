package com.starter.schedule.service.impl;

import com.starter.schedule.client.ClinicClient;
import com.starter.schedule.dto.request.FindAvailableDoctorRequest;
import com.starter.schedule.dto.request.PatientRequest;
import com.starter.schedule.dto.request.ScheduleConsultationRequest;
import com.starter.schedule.dto.request.UpdateScheduleDateRequest;
import com.starter.schedule.dto.response.ConsultationResponse;
import com.starter.schedule.dto.response.ScheduleConsultationResponse;
import com.starter.schedule.entity.Patient;
import com.starter.schedule.exception.ResourceNotFoundException;
import com.starter.schedule.exception.UnavailableScheduleException;
import com.starter.schedule.mapper.PatientMapper;
import com.starter.schedule.messaging.event.CreateConsultationEvent;
import com.starter.schedule.messaging.producer.ScheduleProducer;
import com.starter.schedule.repository.PatientRepository;
import com.starter.schedule.service.ScheduleService;
import com.starter.schedule.util.DateFormatter;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final PatientRepository patientRepository;
    private final ClinicClient clinicClient;
    private final PatientMapper patientMapper;
    private final ScheduleProducer scheduleProducer;

    @Override
    public List<ConsultationResponse> findConsultationsByCpf(String cpf) {
        patientRepository.findByCpf(cpf)
                .orElseThrow(() -> new ResourceNotFoundException("Não existe paciente cadastrado com esse CPF"));

        return clinicClient.findConsultationsByCpf(cpf);
    }

    @Override
    @Transactional
    public ScheduleConsultationResponse createConsultation(ScheduleConsultationRequest request) {
        findOrCreatePatient(request.patient());

        try {
            var findAvailableDoctorRequest = new FindAvailableDoctorRequest(
                    request.specialty(),
                    request.dateTime()
            );

            clinicClient.findAvailableDoctor(findAvailableDoctorRequest);

            var consultationCode = UUID.randomUUID();

            var createConsultationEvent = new CreateConsultationEvent(
                    consultationCode,
                    request.patient().cpf(),
                    request.specialty(),
                    request.dateTime()
            );

            scheduleProducer.sendConsultationRequest(createConsultationEvent);

            return new ScheduleConsultationResponse(
                    String.format("A consulta para o paciente %s será agendada para %s",
                            request.patient().name(),
                            DateFormatter.format(request.dateTime())
                    ),
                    consultationCode
            );
        } catch (FeignException.Conflict ex) {
            throw new UnavailableScheduleException(
                    String.format("Nenhum médico da especialidade %s disponível para %s",
                            request.specialty().getValue(),
                            DateFormatter.format(request.dateTime())
                    )
            );
        }
    }

    @Override
    public ScheduleConsultationResponse updateConsultationDate(UUID id, UpdateScheduleDateRequest request) {
        try {
            var response = clinicClient.updateConsultationDate(id, request);

            return new ScheduleConsultationResponse(
                    String.format("A data da consulta foi alterada para %s com o médico %s",
                            DateFormatter.format(response.dateTime()),
                            response.doctorName()
                    ),
                    response.code()
            );
        } catch (FeignException.NotFound ex) {
            throw new ResourceNotFoundException("Não existe consulta cadastrada com esse código");
        } catch (FeignException.Conflict ex) {
            throw new UnavailableScheduleException(
                    String.format("Nenhum médico disponível para %s",
                            DateFormatter.format(request.dateTime())
                    )
            );
        }

    }

    private Patient findOrCreatePatient(PatientRequest patient) {
        Optional<Patient> patientOptional = patientRepository.findByCpf(patient.cpf());

        return patientOptional.orElseGet(() -> patientRepository.save(patientMapper.toEntity(patient)));
    }
}
