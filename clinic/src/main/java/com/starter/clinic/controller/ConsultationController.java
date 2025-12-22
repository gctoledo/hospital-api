package com.starter.clinic.controller;

import com.starter.clinic.dto.request.AttendConsultationRequest;
import com.starter.clinic.dto.request.ConsultationRequest;
import com.starter.clinic.dto.request.UpdateConsultationDateRequest;
import com.starter.clinic.dto.response.ConsultationResponse;
import com.starter.clinic.dto.response.DiagnoseResult;
import com.starter.clinic.entity.enums.Specialty;
import com.starter.clinic.service.ConsultationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultations")
@RequiredArgsConstructor
public class ConsultationController {

    private final ConsultationService consultationService;

    @GetMapping
    public ResponseEntity<List<ConsultationResponse>> findAll(@RequestParam(required = false) Specialty specialty) {
        List<ConsultationResponse> response = consultationService.findAll(specialty);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/patient/{cpf}")
    public ResponseEntity<List<ConsultationResponse>> findByCpf(@PathVariable String cpf) {
        List<ConsultationResponse> response = consultationService.findByCpf(cpf);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/reserve")
    public ResponseEntity<ConsultationResponse> makeReserve(@Valid @RequestBody ConsultationRequest request) {
        var response = consultationService.makeReserve(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR', 'ADMIN')")
    @PostMapping("/{id}/attend")
    public ResponseEntity<DiagnoseResult> attend(@PathVariable Long id, @Valid @RequestBody AttendConsultationRequest request) {
        var response = consultationService.attend(id, request);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR', 'ADMIN')")
    @PutMapping("/{id}/update/date")
    public ResponseEntity<ConsultationResponse> updateDate(@PathVariable Long id, @Valid @RequestBody UpdateConsultationDateRequest request) {
        var response = consultationService.updateDate(id, request);

        return ResponseEntity.ok(response);
    }
}
