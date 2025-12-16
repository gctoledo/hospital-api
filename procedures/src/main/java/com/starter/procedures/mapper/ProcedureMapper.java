package com.starter.procedures.mapper;

import com.starter.procedures.dto.request.ProcedureRequest;
import com.starter.procedures.dto.response.ProcedureResponse;
import com.starter.procedures.entity.Procedure;
import org.springframework.stereotype.Component;

@Component
public class ProcedureMapper {

    public Procedure toEntity(ProcedureRequest dto) {
        return Procedure.builder()
                .name(dto.name())
                .durationInMinutes(dto.durationInMinutes())
                .complexity(dto.complexity())
                .build();
    }

    public ProcedureResponse toResponse(Procedure procedure) {
        return new ProcedureResponse(
                procedure.getId(),
                procedure.getName(),
                procedure.getDurationInMinutes(),
                procedure.getComplexity()
        );
    }
}
