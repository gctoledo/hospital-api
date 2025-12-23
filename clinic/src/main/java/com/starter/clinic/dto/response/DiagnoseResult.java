package com.starter.clinic.dto.response;

public record DiagnoseResult(
        String diseaseName,
        boolean requireExam,
        Long examId,
        String examName,
        String message
        ) {
}
