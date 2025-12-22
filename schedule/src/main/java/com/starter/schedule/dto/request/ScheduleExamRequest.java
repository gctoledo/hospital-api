package com.starter.schedule.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ScheduleExamRequest(
        @Valid
        @NotNull(message = "Dados do paciente são obrigatórios")
        PatientRequest patient,

        @NotBlank(message = "Nome do procedimento deve ser informado")
        String procedureName,

        @NotNull(message = "Data e hora do exame devem ser informados")
        @Future(message = "A data do exame deve estar no futuro")
        LocalDateTime dateTime
) {
}
