package com.starter.clinic.service.impl;

import com.starter.clinic.dto.response.DiagnoseResult;
import com.starter.clinic.entity.Disease;
import com.starter.clinic.entity.DiseaseSymptom;
import com.starter.clinic.entity.Symptom;
import com.starter.clinic.exception.UnrecognizedException;
import com.starter.clinic.repository.DiseaseSymptomRepository;
import com.starter.clinic.repository.SymptomRepository;
import com.starter.clinic.service.DiagnoseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiagnoseServiceImpl implements DiagnoseService {

    private final SymptomRepository symptomRepository;
    private final DiseaseSymptomRepository diseaseSymptomRepository;

    @Override
    public DiagnoseResult execute(List<String> symptoms) {
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

        // SOLICITAR EXAME
        // SE EMERGENCY SCORE FOR >= 12, SOLICITAR EXAME EMERGENCIAL

        return new DiagnoseResult(
                bestMatch.disease().getId(),
                bestMatch.disease().getName(),
                emergencyScore >= 12
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

    private record DiseaseMatch(
            Disease disease,
            List<DiseaseSymptom> matchedSymptoms,
            double specificity
    ) {}
}