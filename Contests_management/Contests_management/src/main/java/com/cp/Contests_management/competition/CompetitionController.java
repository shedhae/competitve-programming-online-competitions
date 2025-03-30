package com.cp.Contests_management.competition;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompetitionController {
    private final CompetitionService competitionService;
    public CompetitionController(CompetitionService competitionService) {
        this.competitionService = competitionService;
    }

    @PostMapping("/competitions/{creator_id}")
    public CompetitionResponseDto createCompetition(
            @PathVariable("creator_id") Integer userId,
            @RequestBody CompetitionDto competitionDto
    ) {
       return  competitionService.createCompetition(userId, competitionDto);
    }
}
