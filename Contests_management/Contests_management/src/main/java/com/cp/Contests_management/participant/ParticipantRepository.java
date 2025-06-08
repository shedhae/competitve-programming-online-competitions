package com.cp.Contests_management.participant;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Integer> {
    boolean existsByName(String name);

    Participant findByName(String name);
}
