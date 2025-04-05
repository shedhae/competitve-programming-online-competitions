package com.cp.Contests_management.Clarification;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface clarificationRepository extends JpaRepository<clarification, Integer> {
    List<clarification> findByProblemId(Integer problemId);
}