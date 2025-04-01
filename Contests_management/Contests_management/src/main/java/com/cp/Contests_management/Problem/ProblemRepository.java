package com.cp.Contests_management.Problem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cp.Contests_management.competition.Competition;
import com.cp.Contests_management.user.User;

public interface ProblemRepository  extends JpaRepository<Problem,Integer>{

Problem findByName(String name);

boolean existsByName(String name);

List<Problem> findProblemByCompetition(Competition competition);

List<Problem> findProblemByCreator(User user);



}
