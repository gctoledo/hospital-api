package com.starter.clinic.client;

import com.starter.clinic.config.FeignConfig;
import com.starter.clinic.dto.request.external.ReserveExamRequest;
import com.starter.clinic.dto.response.external.ReserveExamResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "lab", url = "${services.lab.url}", configuration = FeignConfig.class)
public interface LabClient {

    @PostMapping("/exams/reserve")
    ReserveExamResponse makeReserve(@RequestBody ReserveExamRequest request);
}
