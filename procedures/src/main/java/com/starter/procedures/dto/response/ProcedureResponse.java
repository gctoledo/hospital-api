package com.starter.procedures.dto.response;

import com.starter.procedures.entity.enums.Complexity;

public record ProcedureResponse(
        Long id,
        String name,
        Integer durationInMinutes,
        Complexity complexity
) {
}
