package com.starter.schedule.client;

import com.starter.schedule.dto.request.FindAvailableDoctorRequest;
import com.starter.schedule.dto.request.UpdateScheduleDateRequest;
import com.starter.schedule.dto.response.ConsultationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "clinic", url = "${services.clinic.url}")
public interface ClinicClient {

    @GetMapping("/consultations/patient/{cpf}")
    List<ConsultationResponse> findConsultationsByCpf(@PathVariable String cpf);

    @PostMapping("/consultations/available")
    void findAvailableDoctor(@RequestBody FindAvailableDoctorRequest request);

    @PutMapping("/consultations/{code}")
    ConsultationResponse updateConsultationDate(@PathVariable UUID code, @RequestBody UpdateScheduleDateRequest request);
}
