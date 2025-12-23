package com.starter.procedures.service.impl;

import com.starter.procedures.dto.request.ExamRequest;
import com.starter.procedures.dto.request.ReserveExamRequest;
import com.starter.procedures.dto.request.ScheduleExamRequest;
import com.starter.procedures.dto.request.UpdateExamDateRequest;
import com.starter.procedures.dto.response.ExamResponse;
import com.starter.procedures.dto.response.ReserveExamResponse;
import com.starter.procedures.dto.response.ScheduleExamResponse;
import com.starter.procedures.entity.Exam;
import com.starter.procedures.entity.enums.Complexity;
import com.starter.procedures.entity.enums.ExamStatus;
import com.starter.procedures.entity.enums.Priority;
import com.starter.procedures.exception.BadRequestException;
import com.starter.procedures.exception.ConflictException;
import com.starter.procedures.exception.ResourceNotFoundException;
import com.starter.procedures.mapper.ExamMapper;
import com.starter.procedures.repository.ExamRepository;
import com.starter.procedures.repository.ProcedureRepository;
import com.starter.procedures.service.ExamService;
import com.starter.procedures.util.DateFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {

    private final ExamRepository examRepository;
    private final ProcedureRepository procedureRepository;
    private final ExamMapper examMapper;

    @Override
    public List<ExamResponse> findByCpf(String cpf) {
        return examRepository.findByPatientCpf(cpf)
                .stream()
                .map(examMapper::toResponse)
                .toList();
    }

    @Transactional
    @Override
    public ReserveExamResponse makeReserve(ReserveExamRequest request) {
        var procedure = procedureRepository.findById(request.procedureId())
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de procedimento não encontrado"));

        var exam = examMapper.toEntity(request, procedure);

        examRepository.save(exam);

        return examMapper.toReserveResponse(exam, procedure);
    }

    @Transactional
    @Override
    public ExamResponse create(ExamRequest request) {
        var procedure = procedureRepository.findByName(request.procedureName())
                .orElseThrow(() -> new ResourceNotFoundException("Não existe procedimento com esse nome"));

        if (procedure.getComplexity().equals(Complexity.HIGH)) {
            throw new BadRequestException("Exames de alta complexidade não podem ser agendados diretamente");
        }

        LocalDateTime startDateTime = request.dateTime();
        LocalDateTime endDateTime = startDateTime.plusMinutes(procedure.getDurationInMinutes());

        if (examRepository.existsConflictingExam(procedure.getId(), startDateTime, endDateTime)) {
            throw new ConflictException(
                    "Já existe um exame agendado para este procedimento no horário solicitado"
            );
        }

        var exam = examMapper.toEntity(request, procedure);

        examRepository.save(exam);

        return examMapper.toResponse(exam);
    }

    @Transactional
    @Override
    public ScheduleExamResponse confirmDate(Long examId, ScheduleExamRequest request) {
        var exam = examRepository.findById(examId)
                .orElseThrow(() -> new ResourceNotFoundException("Exame não encontrado"));

        if (exam.getStatus() != ExamStatus.CREATED) {
            throw new ConflictException("Exame já foi agendado");
        }

        var procedure = exam.getProcedure();
        LocalDateTime startDateTime = request.dateTime();
        LocalDateTime endDateTime = startDateTime.plusMinutes(procedure.getDurationInMinutes());

        checkConflictingExam(exam, startDateTime, endDateTime);

        exam.setStartDateTime(startDateTime);
        exam.setEndDateTime(endDateTime);
        exam.setStatus(ExamStatus.SCHEDULED);

        examRepository.save(exam);

        return new ScheduleExamResponse(
                exam.getId(),
                exam.getStartDateTime(),
                String.format(
                        "%s para o CPF %s foi agendado para %s",
                        procedure.getName(),
                        exam.getPatientCpf(),
                        DateFormatter.format(exam.getStartDateTime())
                )
        );
    }

    @Override
    public ExamResponse updateDate(Long examId, UpdateExamDateRequest request) {
        var exam = examRepository.findById(examId)
                .orElseThrow(() -> new ResourceNotFoundException("Exame não encontrado"));

        if (exam.getStatus().equals(ExamStatus.COMPLETED) || exam.getStatus().equals(ExamStatus.CANCELLED)) {
            throw new ConflictException("Exame foi cancelado ou já foi realizado.");
        }

        var procedure = exam.getProcedure();
        LocalDateTime startDateTime = request.dateTime();
        LocalDateTime endDateTime = startDateTime.plusMinutes(procedure.getDurationInMinutes());

        checkConflictingExam(exam, startDateTime, endDateTime);

        exam.setStatus(ExamStatus.SCHEDULED);
        exam.setStartDateTime(startDateTime);
        exam.setEndDateTime(endDateTime);

        examRepository.save(exam);

        return examMapper.toResponse(exam);
    }

    private void checkConflictingExam(Exam exam, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        var conflictingExam = examRepository.findConflictingExam(
                exam.getProcedure().getId(),
                startDateTime,
                endDateTime
        );

        if (conflictingExam.isPresent()) {
            var conflict = conflictingExam.get();

            if (exam.getPriority() == Priority.EMERGENCY && conflict.getPriority() == Priority.DEFAULT) {
                conflict.setStatus(ExamStatus.CANCELLED);
                conflict.setStartDateTime(null);
                conflict.setEndDateTime(null);
                examRepository.save(conflict);
            } else {
                throw new ConflictException(
                        "Já existe um exame agendado para este procedimento no horário solicitado"
                );
            }
        }
    }
}
