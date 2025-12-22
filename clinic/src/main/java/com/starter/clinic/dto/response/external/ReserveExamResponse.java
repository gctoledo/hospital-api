package com.starter.clinic.dto.response.external;

public record ReserveExamResponse(
        Long examId,
        String procedureName
) {
}
