package com.starter.procedures.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

public record ExamRequest(
        @NotBlank(message = "CPF do paciente deve ser informado")
        @Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 dígitos")
        String patientCpf,

        @NotBlank(message = "Nome do procedimento deve ser informado")
        String procedureName,

        @NotNull(message = "Data e hora do exame devem ser informados")
        @Future(message = "A data do exame deve estar no futuro")
        LocalDateTime dateTime
) {
}
