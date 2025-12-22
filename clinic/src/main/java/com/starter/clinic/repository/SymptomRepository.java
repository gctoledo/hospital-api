package com.starter.clinic.repository;

import com.starter.clinic.entity.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SymptomRepository extends JpaRepository<Symptom, Long> {
    Optional<Symptom> findByName(String name);

    List<Symptom> findByNameIn(List<String> names);
}
