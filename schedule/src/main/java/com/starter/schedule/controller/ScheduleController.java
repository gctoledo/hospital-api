package com.starter.schedule.controller;

import com.starter.schedule.dto.request.ScheduleConsultationRequest;
import com.starter.schedule.dto.request.UpdateScheduleDateRequest;
import com.starter.schedule.dto.response.ConsultationResponse;
import com.starter.schedule.dto.response.ScheduleConsultationResponse;
import com.starter.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("/consultations/{cpf}")
    public ResponseEntity<List<ConsultationResponse>> findConsultationsByCpf(@PathVariable String cpf) {
        var response = scheduleService.findConsultationsByCpf(cpf);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/consultations")
    public ResponseEntity<ScheduleConsultationResponse> createConsultation(@Valid @RequestBody ScheduleConsultationRequest request) {
        var response = scheduleService.createConsultation(request);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/consultations/{id}/update")
    public ResponseEntity<ScheduleConsultationResponse> updateConsultationDate(@PathVariable UUID id, @Valid @RequestBody UpdateScheduleDateRequest request) {
        var response = scheduleService.updateConsultationDate(id, request);

        return ResponseEntity.ok(response);
    }
}
