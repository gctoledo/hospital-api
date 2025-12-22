package com.starter.clinic.entity;

import com.starter.clinic.entity.enums.Severity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "diseases")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"diseaseSymptoms"})
public class Disease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Severity severity = Severity.DEFAULT;

    @Column(name = "procedure_id")
    private Long procedureId;

    @OneToMany(mappedBy = "disease", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DiseaseSymptom> diseaseSymptoms = new HashSet<>();
}
