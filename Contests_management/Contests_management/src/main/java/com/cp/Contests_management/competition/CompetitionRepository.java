package com.cp.Contests_management.competition;

import com.cp.Contests_management.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompetitionRepository extends JpaRepository<Competition, Integer> {
    Competition findByName(String name);

    List<Competition> getCompetitionsByUser(User user);

    boolean existsByName(String name);
}
