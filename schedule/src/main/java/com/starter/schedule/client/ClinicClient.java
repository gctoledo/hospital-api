package com.starter.schedule.client;

import com.starter.schedule.dto.request.ConsultationReserveRequest;
import com.starter.schedule.dto.request.UpdateScheduleDateRequest;
import com.starter.schedule.dto.response.ConsultationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "clinic", url = "${services.clinic.url}")
public interface ClinicClient {

    @GetMapping("/consultations/patient/{cpf}")
    List<ConsultationResponse> findConsultationsByCpf(@PathVariable String cpf);

    @PostMapping("/consultations/reserve")
    ConsultationResponse makeReservation(@RequestBody ConsultationReserveRequest request);

    @PutMapping("/consultations/{id}/update/date")
    ConsultationResponse updateConsultationDate(@PathVariable Long id, @RequestBody UpdateScheduleDateRequest request);
}
