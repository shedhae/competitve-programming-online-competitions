package com.cp.Contests_management.participant_competition;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ParticipantCompetitionController {

    private final ParticipantCompetitionService participantCompetitionService;

    public ParticipantCompetitionController(ParticipantCompetitionService participantCompetitionService) {
        this.participantCompetitionService = participantCompetitionService;
    }

    /*
        This method will allow to a user to register  within
        a competition.

        If the user is the creator of the competition
        he will not be able to compete (participate) .

        and check if the date of participation is before the starting time of the competition
     */
    @PostMapping("/participant_competitions")
    public ParticipantCompetitionResponseDto createParticipationInCompetition(
            @Valid @RequestBody ParticipantCompetitionDto participantCompetitionDto
    ){
        return participantCompetitionService.createParticipationInCompetition(participantCompetitionDto);
    }


    /*
        this response can be used after of within the
        execution of a competition to get the
        rank of a participant
     */
    @GetMapping("/participant_competitions")
    public ParticipantCompetitionResponseDto getParticipantCompetitions(
            @Valid @RequestBody ParticipantCompetitionDto participantCompetitionDto
    ) {
        return participantCompetitionService.getParticipantCompetitions(participantCompetitionDto);
    }
}
