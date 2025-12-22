    package com.starter.clinic.entity;

    import jakarta.persistence.*;
    import lombok.*;

    import java.util.HashSet;
    import java.util.Set;

    @Entity
    @Table(name = "symptoms")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode(exclude = {"diseaseSymptoms"})
    public class Symptom {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, unique = true, length = 100)
        private String name;

        @OneToMany(mappedBy = "symptom", cascade = CascadeType.ALL, orphanRemoval = true)
        private Set<DiseaseSymptom> diseaseSymptoms = new HashSet<>();
    }
