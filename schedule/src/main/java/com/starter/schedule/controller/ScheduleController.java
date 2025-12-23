package com.starter.schedule.controller;

import com.starter.schedule.dto.request.ScheduleConsultationRequest;
import com.starter.schedule.dto.request.ScheduleExamRequest;
import com.starter.schedule.dto.request.external.UpdateDateRequest;
import com.starter.schedule.dto.response.ScheduleConsultationResponse;
import com.starter.schedule.dto.response.ScheduleExamResponse;
import com.starter.schedule.dto.response.external.ConsultationResponse;
import com.starter.schedule.dto.response.external.ExamResponse;
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
    @GetMapping("/exams/{cpf}")
    public ResponseEntity<List<ExamResponse>> findExamsByCpf(@PathVariable String cpf) {
        var response = scheduleService.findExamsByCpf(cpf);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR', 'ADMIN')")
    @PostMapping("/consultations")
    public ResponseEntity<ScheduleConsultationResponse> scheduleConsultation(@Valid @RequestBody ScheduleConsultationRequest request) {
        var response = scheduleService.scheduleConsultation(request);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR', 'ADMIN')")
    @PostMapping("/exams")
    public ResponseEntity<ScheduleExamResponse> scheduleExam(@Valid @RequestBody ScheduleExamRequest request) {
        var response = scheduleService.scheduleExam(request);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR', 'ADMIN')")
    @PutMapping("/consultations/{id}/update/date")
    public ResponseEntity<ScheduleConsultationResponse> updateConsultationDate(@PathVariable Long id, @Valid @RequestBody UpdateDateRequest request) {
        var response = scheduleService.updateConsultationDate(id, request);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR', 'ADMIN')")
    @PutMapping("/exams/{id}/update/date")
    public ResponseEntity<ScheduleExamResponse> updateExamDate(@PathVariable Long id, @Valid @RequestBody UpdateDateRequest request) {
        var response = scheduleService.updateExamDate(id, request);

        return ResponseEntity.ok(response);
    }
}
