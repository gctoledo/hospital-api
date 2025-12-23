package com.starter.procedures.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record UpdateExamDateRequest(
        @NotNull(message = "Data e hora do exame devem ser informados")
        @Future(message = "A data do exame deve estar no futuro")
        LocalDateTime dateTime
) {
}
