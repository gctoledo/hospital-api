package com.starter.schedule.controller;

import com.starter.schedule.dto.request.ScheduleConsultationRequest;
import com.starter.schedule.dto.request.ScheduleExamRequest;
import com.starter.schedule.dto.request.external.UpdateScheduleDateRequest;
import com.starter.schedule.dto.response.ScheduleConsultationResponse;
import com.starter.schedule.dto.response.ScheduleExamResponse;
import com.starter.schedule.dto.response.external.ConsultationResponse;
import com.starter.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR', 'ADMIN')")
    @GetMapping("/consultations/{cpf}")
    public ResponseEntity<List<ConsultationResponse>> findConsultationsByCpf(@PathVariable String cpf) {
        var response = scheduleService.findConsultationsByCpf(cpf);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR', 'ADMIN')")
    @PostMapping("/consultations")
    public ResponseEntity<ScheduleConsultationResponse> createConsultation(@Valid @RequestBody ScheduleConsultationRequest request) {
        var response = scheduleService.createConsultation(request);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR', 'ADMIN')")
    @PostMapping("/exams")
    public ResponseEntity<ScheduleExamResponse> createExam(@Valid @RequestBody ScheduleExamRequest request) {
        var response = scheduleService.createExam(request);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR', 'ADMIN')")
    @PutMapping("/consultations/{id}/update/date")
    public ResponseEntity<ScheduleConsultationResponse> updateConsultationDate(@PathVariable Long id, @Valid @RequestBody UpdateScheduleDateRequest request) {
        var response = scheduleService.updateConsultationDate(id, request);

        return ResponseEntity.ok(response);
    }
}
