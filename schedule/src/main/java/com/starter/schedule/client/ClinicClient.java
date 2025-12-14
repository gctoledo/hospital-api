package com.starter.schedule.client;

import com.starter.schedule.dto.request.CreateConsultationRequest;
import com.starter.schedule.dto.response.ConsultationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "clinic", url = "${services.clinic.url}")
public interface ClinicClient {

    @GetMapping("/consultations/patient/{cpf}")
    List<ConsultationResponse> findConsultationsByCpf(@PathVariable String cpf);

    @PostMapping("/consultations")
    ConsultationResponse createConsultation(@RequestBody CreateConsultationRequest request);
}
