package com.starter.procedures.controller;

import com.starter.procedures.dto.request.ProcedureRequest;
import com.starter.procedures.dto.response.ProcedureResponse;
import com.starter.procedures.service.ProcedureService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/procedures")
@RequiredArgsConstructor
public class ProcedureController {

    private final ProcedureService procedureService;

    @GetMapping
    public ResponseEntity<List<ProcedureResponse>> findAll() {
        List<ProcedureResponse> response = procedureService.findAll();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProcedureResponse> findById(@PathVariable Long id) {
        ProcedureResponse response = procedureService.findById(id);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProcedureResponse> create(@Valid @RequestBody ProcedureRequest request) {
        ProcedureResponse response = procedureService.create(request);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProcedureResponse> update(@PathVariable Long id, @Valid @RequestBody ProcedureRequest request) {
        ProcedureResponse response = procedureService.update(id, request);

        return ResponseEntity.ok(response);
    }
}
