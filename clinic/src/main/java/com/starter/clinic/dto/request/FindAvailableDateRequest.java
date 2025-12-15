package com.starter.clinic.dto.request;

import com.starter.clinic.entity.enums.Specialty;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record FindAvailableDateRequest(
        @NotNull(message = "Especialidade do médico é obrigatório")
        Specialty specialty,

        @NotNull(message = "Data e hora da consulta devem ser informados")
        @Future(message = "A data da consulta deve estar no futuro")
        LocalDateTime dateTime
) {
}
