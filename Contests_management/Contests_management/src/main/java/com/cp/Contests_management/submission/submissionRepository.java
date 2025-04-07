package com.cp.Contests_management.submission;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface submissionRepository  extends JpaRepository<submission,Integer> {

   Optional<submission> findById(Integer submissionId ) ;   
    



}
