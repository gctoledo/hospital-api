package com.starter.schedule.dto.request;

import com.starter.schedule.entity.enums.Gender;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record PatientRequest(
        @NotBlank(message = "Nome é obrigatório")
        @Size(min = 2, max = 255, message = "Nome deve ter entre 2 e 255 caracteres")
        String name,

        @NotBlank(message = "CPF é obrigatório")
        @Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 dígitos")
        String cpf,

        @NotNull(message = "Nascimento é obrigatório")
        @Past(message = "Nascimento deve ser uma data no passado")
        LocalDate birthDate,

        @NotNull(message = "Sexo é obrigatório")
        Gender gender,

        @Pattern(regexp = "\\d{10,11}", message = "Telefone deve ter 10 ou 11 dígitos")
        String phone
) {
}
