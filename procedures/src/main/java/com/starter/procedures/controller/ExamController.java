package com.starter.procedures.controller;

import com.starter.procedures.dto.request.ExamRequest;
import com.starter.procedures.dto.request.ReserveExamRequest;
import com.starter.procedures.dto.request.ScheduleExamRequest;
import com.starter.procedures.dto.request.UpdateExamDateRequest;
import com.starter.procedures.dto.response.ExamResponse;
import com.starter.procedures.dto.response.ReserveExamResponse;
import com.starter.procedures.dto.response.ScheduleExamResponse;
import com.starter.procedures.service.ExamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exams")
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;

    @GetMapping("/patient/{cpf}")
    public ResponseEntity<List<ExamResponse>> findByCpf(@PathVariable String cpf) {
        var exams = examService.findByCpf(cpf);

        return ResponseEntity.ok(exams);
    }

    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    @PostMapping("/reserve")
    public ResponseEntity<ReserveExamResponse> makeReserve(@Valid @RequestBody ReserveExamRequest request) {
        var response = examService.makeReserve(request);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('PATIENT', 'ADMIN')")
    @PostMapping
    public ResponseEntity<ExamResponse> create(@Valid @RequestBody ExamRequest request) {
        var response = examService.create(request);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('PATIENT', 'ADMIN')")
    @PatchMapping("/{id}/confirm/date")
    public ResponseEntity<ScheduleExamResponse> confirmDate(@PathVariable Long id, @Valid @RequestBody ScheduleExamRequest request) {
        var response = examService.confirmDate(id, request);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('PATIENT', 'ADMIN')")
    @PutMapping("/{id}/update/date")
    public ResponseEntity<ExamResponse> updateDateTime(@PathVariable Long id, @Valid @RequestBody UpdateExamDateRequest request) {
        var response = examService.updateDate(id, request);

        return ResponseEntity.ok(response);
    }
}
