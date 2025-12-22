package com.starter.schedule.client;

import com.starter.schedule.config.FeignConfig;
import com.starter.schedule.dto.request.external.CreateExamRequest;
import com.starter.schedule.dto.response.external.CreateExamResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "lab", url = "${services.lab.url}", configuration = FeignConfig.class)
public interface LabClient {

    @PostMapping("/exams")
    CreateExamResponse createExam(@RequestBody CreateExamRequest request);
}
