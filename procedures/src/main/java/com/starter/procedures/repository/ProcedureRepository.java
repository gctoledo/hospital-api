package com.starter.procedures.repository;

import com.starter.procedures.entity.Procedure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcedureRepository extends JpaRepository<Procedure, Long> {
    boolean existsByName(String name);
}
