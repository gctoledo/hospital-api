package com.starter.procedures.repository;

import com.starter.procedures.entity.Procedure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProcedureRepository extends JpaRepository<Procedure, Long> {
    boolean existsByName(String name);

    Optional<Procedure> findByName(String name);
}
