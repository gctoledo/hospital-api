package com.starter.clinic.dto.response;

public record DiagnoseResult(
        Long diseaseId,
        String diseaseName,
        boolean requireExam,
        Long examId,
        String examName,
        String message
        ) {
}
