package com.starter.procedures.service;

import com.starter.procedures.dto.request.ProcedureRequest;
import com.starter.procedures.dto.response.ProcedureResponse;

import java.util.List;

public interface ProcedureService {
    List<ProcedureResponse> findAll();
    ProcedureResponse findById(Long id);
    ProcedureResponse create(ProcedureRequest procedureRequest);
    ProcedureResponse update(Long id, ProcedureRequest procedureRequest);
    void delete(Long id);
}
