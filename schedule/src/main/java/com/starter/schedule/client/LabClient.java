package com.starter.schedule.client;

import com.starter.schedule.config.FeignConfig;
import com.starter.schedule.dto.request.external.ExamRequest;
import com.starter.schedule.dto.request.external.UpdateDateRequest;
import com.starter.schedule.dto.response.external.ExamResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "lab", url = "${services.lab.url}", configuration = FeignConfig.class)
public interface LabClient {

    @GetMapping("/exams/patient/{cpf}")
    List<ExamResponse> findExamsByCpf(@PathVariable String cpf);

    @PostMapping("/exams")
    ExamResponse createExam(@RequestBody ExamRequest request);

    @PutMapping("/exams/{id}/update/date")
    ExamResponse updateExamDate(@PathVariable Long id, @RequestBody UpdateDateRequest request);
}
