package com.starter.procedures.entity;

import com.starter.procedures.entity.enums.ProcedurePriority;
import com.starter.procedures.entity.enums.ProcedureStatus;
import com.starter.procedures.entity.enums.ProcedureType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "procedures")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Procedure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patient_cpf", nullable = false, length = 11)
    private String patientCpf;

    @Column(name = "doctor_crm")
    private String doctorCrm;

    @Enumerated(EnumType.STRING)
    @Column(name = "exam_type", nullable = false)
    private ProcedureType procedureType;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private ProcedureStatus status = ProcedureStatus.SCHEDULED;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private ProcedurePriority priority = ProcedurePriority.DEFAULT;
}
