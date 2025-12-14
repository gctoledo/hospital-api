package com.starter.schedule.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record UpdateScheduleDateRequest(
        @NotNull(message = "Data e hora da consulta devem ser informados")
        @Future(message = "A data da consulta deve estar no futuro")
        LocalDateTime dateTime
) {
}
