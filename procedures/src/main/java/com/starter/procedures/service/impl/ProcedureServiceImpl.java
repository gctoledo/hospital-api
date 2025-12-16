package com.starter.procedures.service.impl;

import com.starter.procedures.dto.request.ProcedureRequest;
import com.starter.procedures.dto.response.ProcedureResponse;
import com.starter.procedures.entity.Procedure;
import com.starter.procedures.exception.ConflictException;
import com.starter.procedures.exception.ResourceNotFoundException;
import com.starter.procedures.mapper.ProcedureMapper;
import com.starter.procedures.repository.ProcedureRepository;
import com.starter.procedures.service.ProcedureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcedureServiceImpl implements ProcedureService {

    private final ProcedureRepository procedureRepository;
    private final ProcedureMapper procedureMapper;

    @Override
    public ProcedureResponse findById(Long id) {
        Procedure procedure = procedureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Procedimento não encontrado"));

        return procedureMapper.toResponse(procedure);
    }

    @Override
    public List<ProcedureResponse> findAll() {
        return procedureRepository.findAll().stream()
                .map(procedureMapper::toResponse)
                .toList();
    }

    @Override
    public ProcedureResponse create(ProcedureRequest request) {
        boolean nameAlreadyExists = procedureRepository.existsByName(request.name());

        if (nameAlreadyExists) {
            throw new ConflictException("Nome da procedimento ja existe");
        }

        Procedure procedure = procedureMapper.toEntity(request);

        return procedureMapper.toResponse(procedureRepository.save(procedure));
    }

    @Override
    public ProcedureResponse update(Long id, ProcedureRequest request) {
        Procedure procedure = procedureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Procedimento não encontrado"));

        if (!procedure.getName().equals(request.name()) && procedureRepository.existsByName(request.name())) {
            throw new ConflictException("Já existe procedimento com esse nome");
        }

        procedure.setName(request.name());
        procedure.setDurationInMinutes(request.durationInMinutes());
        procedure.setComplexity(request.complexity());

        return procedureMapper.toResponse(procedureRepository.save(procedure));
    }

    @Override
    public void delete(Long id) {
        procedureRepository.deleteById(id);
    }
}
