package com.cp.Contests_management.Problem;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cp.Contests_management.competition.Competition;
import com.cp.Contests_management.user.User;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/problems")
public class ProblemController {

    private final ProblemService problemService;

    public ProblemController(ProblemService problemService) {
        this.problemService = problemService;
    }

    // Create a new problem and associate it with a competition
    @PostMapping("/create/{competitionId}/{userId}")
    public ResponseEntity<ProblemResponseDto> createProblem(
            @Valid @RequestBody ProblemDto problem,
           @PathVariable ("competition_Id") Integer competitionId ,
             @PathVariable ("user_Id") Integer userId) {
        ProblemResponseDto createdProblem = problemService.createProblem(competitionId,problem,userId);
        return ResponseEntity.ok(createdProblem);
    }

    // Get a problem by its ID
    @GetMapping("/{problemId}")
    public ResponseEntity<ProblemResponseDto> getProblemById(@PathVariable Integer problemId) {
        return ResponseEntity.ok(problemService.getProblemById(problemId));
    }

    //  Get all problems by competition
    @GetMapping("/competition/{competitionId}")
    public ResponseEntity<List<ProblemResponseDto>> getProblemsByCompetition(@PathVariable Integer competitionId) {
        return ResponseEntity.ok(problemService.getProblemByCompetition(competitionId));
    }

    // Get all problems created by one creator
    @GetMapping("/creator/{userId}")
    public ResponseEntity<List<ProblemResponseDto>> getProblemsByCreator(@PathVariable Integer userId) {
        return ResponseEntity.ok(problemService.getProblemsByCreator(userId));
    }

    //  Get a problem by its title 
    @GetMapping("/title/{name}")
    public ResponseEntity<ProblemResponseDto> getProblemByName(@PathVariable String name) {
        return ResponseEntity.ok(problemService.getProblemByName(name));
    }

    //  Update a problem
    @PutMapping("{problemId}")
    public ResponseEntity<ProblemResponseDto> updateProblem(
            @PathVariable Integer problemId,
            @RequestBody ProblemDto problemDto) {

        return ResponseEntity.ok(problemService.updateProblemById(problemId, problemDto));
    }

    //  Add an existing problem to a competition
    @PutMapping("/{problemId}/add-to-competition/{competitionId}")
    public ResponseEntity<ProblemResponseDto> addProblemToCompetition(
            @PathVariable Integer problemId,
            @PathVariable Integer competitionId) {

        return ResponseEntity.ok(problemService.addProblemToCompetition(problemId, competitionId));
    }

    // Remove a problem from a competition
    @DeleteMapping("{problemId}/remove-from-competition")
    public void removeProblemFromCompetition(@PathVariable Integer problemId) {
       problemService.removeProblemFromCompetition(problemId);
    }
}

