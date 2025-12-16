package com.starter.procedures.dto.request;

import com.starter.procedures.entity.enums.Complexity;
import jakarta.validation.constraints.*;

public record ProcedureRequest(
        @NotBlank(message = "Nome é obrigatório")
        @Size(min = 5, max = 255, message = "Nome deve ter entre 5 e 100 caracteres")
        String name,

        @NotNull(message = "Duração é obrigatória")
        @Min(value = 5, message = "Duração deve ser maior ou igual a 5 minutos")
        @Max(value = 180, message = "Duração deve ser menor ou igual a 180 minutos")
        Integer durationInMinutes,

        @NotNull(message = "Complexidade é obrigatória")
        Complexity complexity
) {
}
