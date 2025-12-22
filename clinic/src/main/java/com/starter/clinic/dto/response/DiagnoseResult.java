package com.starter.clinic.dto.response;

public record DiagnoseResult(
        Long diseaseId,
        String diseaseName,
        boolean requiresEmergencyExam
) {
}
