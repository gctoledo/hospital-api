package com.starter.clinic.controller;

import com.starter.clinic.dto.request.ConsultationRequest;
import com.starter.clinic.dto.response.ConsultationResponse;
import com.starter.clinic.service.ConsultationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping
    public ResponseEntity<ConsultationResponse> create(@Valid @RequestBody ConsultationRequest request) {
        var response = consultationService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
