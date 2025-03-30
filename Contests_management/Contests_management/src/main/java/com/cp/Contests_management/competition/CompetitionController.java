package com.cp.Contests_management.competition;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CompetitionController {
    private final CompetitionService competitionService;
    public CompetitionController(CompetitionService competitionService) {
        this.competitionService = competitionService;
    }

    @PostMapping("/competitions/{creator_id}")
    public CompetitionResponseDto createCompetition(
            @PathVariable("creator_id") Integer userId,
            @Valid @RequestBody CompetitionDto competitionDto
    ) {
       return  competitionService.createCompetition(userId, competitionDto);
    }

    @GetMapping("/competitions")
    public List<CompetitionResponseDto> getAllCompetitions() {
        return competitionService.getAllCompetitions();
    }
    @GetMapping("/competitions/{competition_id}")
    public CompetitionResponseDto getCompetitionById(
            @PathVariable("competition_id") Integer competitionId
    ) {
        return competitionService.getCompetitionById(competitionId);
    }

    @GetMapping("/competitions/search/{competition_name}")
    public CompetitionResponseDto getCompetitionByName(
            @PathVariable("competition_name") String competitionName
    ){
        return competitionService.getCompetitionByName(competitionName);
    }

    @PutMapping("/competitions/{competition_id}")
    public CompetitionResponseDto updateCompetitionById(
            @PathVariable("competition_id") Integer competitionId,
            @Valid @RequestBody CompetitionDto competitionDto
    )
    {
        return competitionService.updateCompetitionById(competitionId,competitionDto);

    }

}
