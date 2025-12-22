package com.starter.procedures.repository;

import com.starter.procedures.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {

    @Query("""
        SELECT COUNT(e) > 0 FROM Exam e
        WHERE e.procedure.id = :procedureId
        AND e.status NOT IN ('CANCELLED', 'COMPLETED')
        AND e.startDateTime IS NOT NULL
        AND e.endDateTime IS NOT NULL
        AND (e.startDateTime < :endDateTime AND e.endDateTime > :startDateTime)
    """)
    boolean existsConflictingExam(
        @Param("procedureId") Long procedureId,
        @Param("startDateTime") LocalDateTime startDateTime,
        @Param("endDateTime") LocalDateTime endDateTime
    );

    @Query("""
        SELECT e FROM Exam e
        WHERE e.procedure.id = :procedureId
        AND e.status NOT IN ('CANCELLED', 'COMPLETED')
        AND e.startDateTime IS NOT NULL
        AND e.endDateTime IS NOT NULL
        AND (e.startDateTime < :endDateTime AND e.endDateTime > :startDateTime)
    """)
    Optional<Exam> findConflictingExam(
        @Param("procedureId") Long procedureId,
        @Param("startDateTime") LocalDateTime startDateTime,
        @Param("endDateTime") LocalDateTime endDateTime
    );
}
