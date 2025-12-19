package com.starter.schedule.controller;

import com.starter.schedule.dto.request.PatientRequest;
import com.starter.schedule.dto.response.PatientResponse;
import com.starter.schedule.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> findById(@PathVariable Long id) {
        var response = patientService.findById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PatientResponse>> findAll() {
        var response = patientService.findAll();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<PatientResponse> create(@Valid @RequestBody PatientRequest request) {
        var response = patientService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponse> update(@PathVariable Long id, @Valid @RequestBody PatientRequest request) {
        var response = patientService.update(id, request);

        return ResponseEntity.ok(response);
    }
}
