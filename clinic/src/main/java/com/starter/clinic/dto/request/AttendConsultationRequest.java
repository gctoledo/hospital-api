package com.starter.clinic.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record AttendConsultationRequest(
        @NotBlank(message = "CPF do paciente deve ser informado")
        @Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 dígitos")
        String patientCpf,

        @NotNull(message = "Lista de sintomas deve ser informada")
        @NotEmpty(message = "Lista de sintomas deve ter pelo menos um sintoma")
        List<String> symptoms
) {
}
