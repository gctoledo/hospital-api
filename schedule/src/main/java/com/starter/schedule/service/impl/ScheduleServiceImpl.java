package com.starter.schedule.service.impl;

import com.starter.schedule.client.ClinicClient;
import com.starter.schedule.client.LabClient;
import com.starter.schedule.dto.request.PatientRequest;
import com.starter.schedule.dto.request.ScheduleConsultationRequest;
import com.starter.schedule.dto.request.ScheduleExamRequest;
import com.starter.schedule.dto.request.external.ConsultationReserveRequest;
import com.starter.schedule.dto.request.external.CreateExamRequest;
import com.starter.schedule.dto.request.external.UpdateScheduleDateRequest;
import com.starter.schedule.dto.response.ScheduleConsultationResponse;
import com.starter.schedule.dto.response.ScheduleExamResponse;
import com.starter.schedule.dto.response.external.ConsultationResponse;
import com.starter.schedule.entity.Patient;
import com.starter.schedule.exception.BadRequestException;
import com.starter.schedule.exception.ConflictException;
import com.starter.schedule.exception.ResourceNotFoundException;
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

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final PatientRepository patientRepository;
    private final ClinicClient clinicClient;
    private final LabClient labClient;
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
        var patient = findOrCreatePatient(request.patient());

        try {
            var makeReservationRequest = new ConsultationReserveRequest(
                    request.specialty(),
                    patient.getCpf(),
                    request.dateTime()
            );

            var reservation = clinicClient.makeReservation(makeReservationRequest);

            var createConsultationEvent = new CreateConsultationEvent(
                    reservation.id()
            );

            scheduleProducer.sendConsultationRequest(createConsultationEvent);

            return new ScheduleConsultationResponse(
                    reservation.id(),
                    String.format("A consulta para o paciente %s foi reservada para %s",
                            request.patient().name(),
                            DateFormatter.format(request.dateTime())
                    )
            );
        } catch (FeignException.Conflict ex) {
            throw new ConflictException(
                    String.format("Nenhum médico da especialidade %s disponível para %s",
                            request.specialty().getValue(),
                            DateFormatter.format(request.dateTime())
                    )
            );
        }
    }

    @Override
    public ScheduleExamResponse createExam(ScheduleExamRequest request) {
        var patient = findOrCreatePatient(request.patient());

        try {
            var examRequest = new CreateExamRequest(
                    patient.getCpf(),
                    request.procedureName(),
                    request.dateTime()
            );

            var response = labClient.createExam(examRequest);

            return new ScheduleExamResponse(
                    response.examId(),
                    response.procedureName(),
                    response.startDateTime(),
                    response.endDateTime(),
                    String.format(
                            "%s agendado para %s na data %s.",
                            response.procedureName(),
                            patient.getName(),
                            DateFormatter.format(response.startDateTime())
                    )
            );
        } catch (FeignException.BadRequest ex) {
            throw new BadRequestException("Exames de alta complexidade não podem ser agendados diretamente");
        } catch (FeignException.Conflict ex) {
            throw new ConflictException("Já existe um exame agendado para este procedimento no horário solicitado");
        } catch (FeignException.NotFound ex) {
            throw new ResourceNotFoundException("Não existe procedimento com esse nome");
        }
    }

    @Override
    public ScheduleConsultationResponse updateConsultationDate(Long id, UpdateScheduleDateRequest request) {
        try {
            var response = clinicClient.updateConsultationDate(id, request);

            return new ScheduleConsultationResponse(
                    response.id(),
                    String.format("A data da consulta foi alterada para %s",
                            DateFormatter.format(response.startDateTime())
                    )
            );
        } catch (FeignException.NotFound ex) {
            throw new ResourceNotFoundException("Consulta não encontrada.");
        } catch (FeignException.Conflict ex) {
            throw new ConflictException(
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
