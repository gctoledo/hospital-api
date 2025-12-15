package com.starter.clinic.controller;

import com.starter.clinic.dto.request.DoctorRequest;
import com.starter.clinic.dto.request.FindAvailableDoctorsRequest;
import com.starter.clinic.dto.response.DoctorResponse;
import com.starter.clinic.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponse> findById(@PathVariable Long id) {
        DoctorResponse response = doctorService.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/crm/{crm}")
    public ResponseEntity<DoctorResponse> findByCrm(@PathVariable String crm) {
        DoctorResponse response = doctorService.findByCrm(crm);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<DoctorResponse> create(@Valid @RequestBody DoctorRequest request) {
        DoctorResponse response = doctorService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/available")
    public ResponseEntity<List<DoctorResponse>> findAvailableDoctors(@Valid @RequestBody FindAvailableDoctorsRequest request) {
        List<DoctorResponse> response = doctorService.findAvailableDoctors(request.specialty(), request.dateTime());

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody DoctorRequest request) {
        DoctorResponse response = doctorService.update(id, request);
        return ResponseEntity.ok(response);
    }
}
