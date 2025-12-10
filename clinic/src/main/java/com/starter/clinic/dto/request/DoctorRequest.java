package com.starter.clinic.dto.request;

import com.starter.clinic.entity.enums.Specialty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record DoctorRequest(
        @NotBlank(message = "Nome é obrigatório")
        @Size(min = 5, max = 255, message = "Nome deve ter entre 5 e 255 caracteres")
        String name,

        @NotBlank(message = "CRM é obrigatório")
        @Pattern(
                regexp = "^\\d{4,8}/[A-Z]{2,3}$",
                message = "Formato de CRM inválido. Esperado: <número>/<UF>, e.g., 12345/SP"
        )
        String crm,

        @NotNull(message = "Especialidade é obrigatória")
        Specialty specialty
) {
}
