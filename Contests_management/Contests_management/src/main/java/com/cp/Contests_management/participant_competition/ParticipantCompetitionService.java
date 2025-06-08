package com.cp.Contests_management.participant_competition;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class ParticipantCompetitionService {
    private final ParticipantCompetitionRepository participantCompetitionRepository;
    private final ParticipantCompetitionMapper participantCompetitionMapper;


    public ParticipantCompetitionService(
            ParticipantCompetitionRepository participantCompetitionRepository,
            ParticipantCompetitionMapper participantCompetitionMapper
    ) {
        this.participantCompetitionRepository = participantCompetitionRepository;
        this.participantCompetitionMapper = participantCompetitionMapper;
    }

    /*
        This method will allow to a user to register  within
        a competition.

        If the user is the creator of the competition
        he will not be able to compete (participate) .
     */
    public ParticipantCompetitionResponseDto createParticipationInCompetition(
            ParticipantCompetitionDto participantCompetitionDto
    ) {
        ParticipantCompetitionTuple participantCompetitionTuple = participantCompetitionMapper
                .existingParticipantCompetition(participantCompetitionDto);

        if (!participantCompetitionTuple.exists()) {
            if (participantCompetitionTuple.competition() == null
                    && participantCompetitionTuple.participant() == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Competition and Participant are not found");
            } else if (participantCompetitionTuple.competition() == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Competition not found");
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Participant not found");
            }
        }
        /*
            Check if the participant is the creator of the competition
         */
        if(participantCompetitionTuple
                .competition()
                .getUser()
                .getName()
                .equals(participantCompetitionDto.participantName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "You are the creator of this competition," +
                    "You are not allowed to participate in this competition");
        }
        /*
            check if the participant already registered in the competition
        */
        ParticipantsCompetitionsId id = new ParticipantsCompetitionsId(
                participantCompetitionTuple.participant().getId(),
                participantCompetitionTuple.competition().getId()
        );
        if (participantCompetitionRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Participant is already registered with this competition");
        }
        /*
            Check if the time of the registration is before the start of the contest
         */
        if(LocalDateTime.now().isAfter(participantCompetitionTuple.competition().getStartTime())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Competition might be running or already finished");
        }
        /*
            If not registered, persist it then return the response
        */
        ParticipantCompetition participantCompetitionSaved = participantCompetitionRepository.save(
                participantCompetitionMapper.dtoToParticipantCompetition(participantCompetitionDto)
        );

        return participantCompetitionMapper
                    .participantCompetitionToParticipantCompetitionResponseDto(participantCompetitionSaved);
    }



    /*
        this method can be used after or within the
        execution of a competition to get the
        rank of a participant
     */
    public ParticipantCompetitionResponseDto getParticipantCompetitions(
            ParticipantCompetitionDto participantCompetitionDto
    ) {
        ParticipantCompetitionTuple participantCompetitionTuple = participantCompetitionMapper
                .existingParticipantCompetition(participantCompetitionDto);
        if(participantCompetitionTuple.exists()) {
            ParticipantsCompetitionsId id = new ParticipantsCompetitionsId(
                    participantCompetitionTuple.participant().getId(),
                    participantCompetitionTuple.competition().getId()
            );
            ParticipantCompetition participantCompetition = participantCompetitionRepository.findById(id).orElse(null);
            return participantCompetitionMapper
                    .participantCompetitionToParticipantCompetitionResponseDto(participantCompetition);
        }
        if(participantCompetitionTuple.competition()==null
                && participantCompetitionTuple.participant()==null)
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Competition and Participant  are not found"
            );
        else if(participantCompetitionTuple.competition()==null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Competition not  found");
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Participant not found");
    }


}
