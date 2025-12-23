package com.starter.clinic.service.impl;

import com.starter.clinic.client.LabClient;
import com.starter.clinic.dto.request.external.ReserveExamRequest;
import com.starter.clinic.dto.response.DiagnoseResult;
import com.starter.clinic.dto.response.external.ReserveExamResponse;
import com.starter.clinic.entity.Disease;
import com.starter.clinic.entity.DiseaseSymptom;
import com.starter.clinic.entity.Symptom;
import com.starter.clinic.entity.enums.ExamPriority;
import com.starter.clinic.exception.UnrecognizedException;
import com.starter.clinic.repository.DiseaseSymptomRepository;
import com.starter.clinic.repository.SymptomRepository;
import com.starter.clinic.service.DiagnoseService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiagnoseServiceImpl implements DiagnoseService {

    private final SymptomRepository symptomRepository;
    private final DiseaseSymptomRepository diseaseSymptomRepository;
    private final LabClient labClient;

    @Override
    public DiagnoseResult execute(String patientCpf, List<String> symptoms) {
        List<Symptom> validSymptoms = symptomRepository.findByNameIn(symptoms);

        if (validSymptoms.size() < 2) {
            throw new UnrecognizedException("Pelo menos 2 sintomas reconhecidos são necessários para diagnóstico");
        }

        List<String> validSymptomNames = validSymptoms.stream()
                .map(Symptom::getName)
                .toList();

        List<DiseaseSymptom> diseaseSymptoms = diseaseSymptomRepository.findBySymptomNames(validSymptomNames);

        if (diseaseSymptoms.isEmpty()) {
            throw new UnrecognizedException("Nenhuma doença encontrada para os sintomas fornecidos");
        }

        DiseaseMatch bestMatch = findMostProbableDisease(diseaseSymptoms);

        int emergencyScore = calculateEmergencyScore(bestMatch);

        Disease disease = bestMatch.disease();

        boolean requireExam = disease.getProcedureId() != null;
        Long examId = null;
        String examName = null;
        String message = "Solicitação de exame cadastrada. Utilize o ID para agendar o horário em nosso sistema.";

        if (requireExam) {
            ExamPriority priority = determinePriority(emergencyScore);
            var examResponse = requestExam(patientCpf, disease.getProcedureId(), priority);

            if (examResponse != null) {
                examId = examResponse.examId();
                examName = examResponse.procedureName();
            } else {
                message = "Não foi possível solicitar o exame.";
            }
        }

        return new DiagnoseResult(
                disease.getName(),
                requireExam,
                examId,
                examName,
                message
        );
    }

    private DiseaseMatch findMostProbableDisease(List<DiseaseSymptom> diseaseSymptoms) {
        Map<Disease, List<DiseaseSymptom>> diseaseSymptomsMap = diseaseSymptoms.stream()
                .collect(Collectors.groupingBy(DiseaseSymptom::getDisease));

        return diseaseSymptomsMap.entrySet().stream()
                .map(entry -> {
                    Disease disease = entry.getKey();
                    List<DiseaseSymptom> matched = entry.getValue();
                    double specificity = matched.stream()
                            .mapToDouble(DiseaseSymptom::getSpecificity)
                            .sum();
                    return new DiseaseMatch(disease, matched, specificity);
                })
                .max(Comparator
                        .<DiseaseMatch>comparingInt(dm -> dm.matchedSymptoms().size())
                        .thenComparingDouble(DiseaseMatch::specificity))
                .orElseThrow(() -> new UnrecognizedException("Nenhuma doença encontrada"));
    }

    private int calculateEmergencyScore(DiseaseMatch match) {
        int diseaseScore = match.disease().getSeverity().getScore();

        int symptomsScore = match.matchedSymptoms().stream()
                .mapToInt(ds -> ds.getSeverity().getScore())
                .sum();

        return diseaseScore + symptomsScore;
    }

    private ExamPriority determinePriority(int emergencyScore) {
        return emergencyScore >= 12 ? ExamPriority.EMERGENCY : ExamPriority.DEFAULT;
    }

    private ReserveExamResponse requestExam(String patientCpf, Long procedureId, ExamPriority priority) {
        try {
            ReserveExamRequest request = new ReserveExamRequest(
                    procedureId,
                    patientCpf,
                    priority
            );

            ReserveExamResponse response = labClient.makeReserve(request);

            log.info("Exame solicitado com sucesso - Procedimento: {}, Paciente: {}, Prioridade: {}",
                    procedureId, patientCpf, priority);

            return response;
        } catch (FeignException e) {
            log.error("Erro ao solicitar exame - Procedimento: {}, Paciente: {}", procedureId, patientCpf, e);
            return null;
        }
    }

    private record DiseaseMatch(
            Disease disease,
            List<DiseaseSymptom> matchedSymptoms,
            double specificity
    ) {}
}