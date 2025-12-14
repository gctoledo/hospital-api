package com.starter.schedule.dto.request;

import com.starter.schedule.entity.enums.Specialty;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

public record CreateConsultationRequest(
        @NotBlank(message = "CPF do paciente deve ser informado")
        @Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 dígitos")
        String patientCpf,

        @NotNull(message = "Especialidade do médico é obrigatório")
        Specialty specialty,

        @NotNull(message = "Data e hora da consulta devem ser informados")
        @Future(message = "A data da consulta deve estar no futuro")
        LocalDateTime dateTime
) {
}
