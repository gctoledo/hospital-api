package com.starter.clinic.controller;

import com.starter.clinic.dto.request.FindAvailableDateRequest;
import com.starter.clinic.dto.request.UpdateConsultationDateRequest;
import com.starter.clinic.dto.response.ConsultationResponse;
import com.starter.clinic.service.ConsultationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/consultations")
@RequiredArgsConstructor
public class ConsultationController {

    private final ConsultationService consultationService;

    @GetMapping("/patient/{cpf}")
    public ResponseEntity<List<ConsultationResponse>> findByCpf(@PathVariable String cpf) {
        List<ConsultationResponse> response = consultationService.findByCpf(cpf);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/available")
    public ResponseEntity<Void> validateAvaialbleDate(@Valid @RequestBody FindAvailableDateRequest request) {
        var response = consultationService.findAvailableDoctor(request.specialty(), request.dateTime());

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{code}")
    public ResponseEntity<ConsultationResponse> updateDate(@PathVariable UUID code, @Valid @RequestBody UpdateConsultationDateRequest request) {
        var response = consultationService.updateDate(code, request);

        return ResponseEntity.ok(response);
    }
}
