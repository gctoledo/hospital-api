package com.starter.clinic.dto.request.external;

import com.starter.clinic.entity.enums.ExamPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record ReserveExamRequest(
        @NotNull(message = "ID do procedimento é obrigatório")
        @Positive(message = "Procedimento deve ter um ID válido")
        Long procedureId,

        @NotBlank(message = "CPF do paciente deve ser informado")
        @Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 dígitos")
        String patientCpf,

        @NotNull(message = "Prioridade do exame é obrigatório")
        ExamPriority priority
) {
}
