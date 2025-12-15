package com.starter.schedule.dto.request;

import com.starter.schedule.entity.enums.Specialty;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record FindAvailableDoctorRequest(
        @NotNull(message = "Especialidade do médico é obrigatório")
        Specialty specialty,

        @NotNull(message = "Data e hora da consulta devem ser informados")
        @Future(message = "A data da consulta deve estar no futuro")
        LocalDateTime dateTime
) {
}
