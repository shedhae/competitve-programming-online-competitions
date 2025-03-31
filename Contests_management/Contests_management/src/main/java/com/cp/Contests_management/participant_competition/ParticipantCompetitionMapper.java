package com.cp.Contests_management.participant_competition;

import com.cp.Contests_management.competition.Competition;
import com.cp.Contests_management.competition.CompetitionRepository;
import com.cp.Contests_management.participant.Participant;
import com.cp.Contests_management.participant.ParticipantRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ParticipantCompetitionMapper {

    private final ParticipantRepository participantRepository;
    private final CompetitionRepository competitionRepository;

    public ParticipantCompetitionMapper(
            ParticipantRepository participantRepository,
            CompetitionRepository competitionRepository
    ) {
        this.participantRepository = participantRepository;
        this.competitionRepository = competitionRepository;
    }

    public ParticipantCompetition dtoToParticipantCompetition(ParticipantCompetitionDto dto) {
        if(dto == null) {
            return null;
        }
        Participant participant = participantRepository.findByName(dto.participantName());
        if(participant == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Participant not found");
        }
        Competition competition = competitionRepository.findByName(dto.CompetitionName());
        if(competition == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Competition not found");
        }
        ParticipantCompetition participantCompetition = new ParticipantCompetition();
        participantCompetition.setCompetition(competition);
        participantCompetition.setParticipant(participant);
        ParticipantsCompetitionsId id = new ParticipantsCompetitionsId(
                participant.getId(),
                competition.getId()
        );
        participantCompetition.setId(id);
        return participantCompetition;

    }

    public ParticipantCompetitionResponseDto participantCompetitionToParticipantCompetitionResponseDto(
            ParticipantCompetition participantCompetition
    ) {
        if(participantCompetition == null) {
            return null;
        }
        return new ParticipantCompetitionResponseDto(
                participantCompetition.getParticipant().getName(),
                participantCompetition.getCompetition().getName(),
                participantCompetition.getRank()
        );
    }

    /*
        This function will test if the participant and the competition
        exist in the database.

        if both exist it will return <True, Participant, Competition>
        if Participant doesn't exist it will return < False, Null , Competition>
        if Competition doesn't exist it will return < False, Participant, Null>
        if both doesn't exist it will return < False, Null , Null >
     */
    public ParticipantCompetitionTuple existingParticipantCompetition(ParticipantCompetitionDto dto) {
        Participant participant = participantRepository.findByName(dto.participantName());
        Competition competition = competitionRepository.findByName(dto.CompetitionName());
        boolean exists = participant != null && competition != null;
        return new ParticipantCompetitionTuple(exists, participant, competition);
    }




}
