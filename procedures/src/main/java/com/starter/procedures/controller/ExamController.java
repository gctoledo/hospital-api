package com.starter.procedures.controller;

import com.starter.procedures.dto.request.ExamRequest;
import com.starter.procedures.dto.request.ReserveExamRequest;
import com.starter.procedures.dto.request.ScheduleExamRequest;
import com.starter.procedures.dto.response.ExamResponse;
import com.starter.procedures.dto.response.ReserveExamResponse;
import com.starter.procedures.dto.response.ScheduleExamResponse;
import com.starter.procedures.service.ExamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exams")
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;

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
    @PatchMapping("/{id}/schedule")
    public ResponseEntity<ScheduleExamResponse> schedule(@PathVariable Long id, @Valid @RequestBody ScheduleExamRequest request) {
        var response = examService.schedule(id, request);

        return ResponseEntity.ok(response);
    }
}
