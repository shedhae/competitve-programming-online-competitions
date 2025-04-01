package com.cp.Contests_management.Problem;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/problems")
public class ProblemController {

    private final ProblemService problemService;

    public ProblemController(ProblemService problemService) {
        this.problemService = problemService;
    }

    // Create a new problem and associate it with a competition
    @PostMapping("/create")
    public ResponseEntity<ProblemResponseDto> createProblem(
            @RequestParam Integer userId,
            @RequestParam Integer competitionId,
            @RequestBody ProblemDto problemDto) {

        ProblemResponseDto createdProblem = problemService.createProblem(userId, problemDto, competitionId);
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

    // Get all problems created by creator
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
    @PutMapping("/{problemId}/update")
    public ResponseEntity<ProblemResponseDto> updateProblem(
            @PathVariable Integer problemId,
            @RequestBody ProblemDto problemDto) {

        return ResponseEntity.ok(problemService.updateCompetitionById(problemId, problemDto));
    }

    //  Add an existing problem to a competition
    @PutMapping("/{problemId}/add-to-competition/{competitionId}")
    public ResponseEntity<ProblemResponseDto> addProblemToCompetition(
            @PathVariable Integer problemId,
            @PathVariable Integer competitionId) {

        return ResponseEntity.ok(problemService.addProblemToCompetition(problemId, competitionId));
    }

    // Remove a problem from a competition
    @PutMapping("/{problemId}/remove-from-competition")
    public ResponseEntity<ProblemResponseDto> removeProblemFromCompetition(@PathVariable Integer problemId) {
        return ResponseEntity.ok(problemService.removeProblemFromCompetition(problemId));
    }
}

