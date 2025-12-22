package com.starter.clinic.repository;

import com.starter.clinic.entity.DiseaseSymptom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiseaseSymptomRepository extends JpaRepository<DiseaseSymptom, Long> {
    @Query("""
        SELECT ds FROM DiseaseSymptom ds
        JOIN FETCH ds.disease
        JOIN FETCH ds.symptom
        WHERE ds.symptom.name IN :symptomNames
    """)
    List<DiseaseSymptom> findBySymptomNames(@Param("symptomNames") List<String> symptomNames);
}
