package com.starter.clinic.entity;

import com.starter.clinic.entity.enums.Severity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "disease_symptoms")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"disease", "symptom"})
public class DiseaseSymptom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disease_id")
    private Disease disease;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "symptom_id")
    private Symptom symptom;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Severity severity = Severity.DEFAULT;

    @Column(nullable = false)
    private double specificity = 0.5;
}

