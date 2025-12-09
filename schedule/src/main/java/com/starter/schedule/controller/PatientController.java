package com.starter.schedule.controller;

import com.starter.schedule.dto.request.PatientRequest;
import com.starter.schedule.dto.response.PatientResponse;
import com.starter.schedule.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<PatientResponse> create(@Valid @RequestBody PatientRequest request) {
        var response = patientService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
