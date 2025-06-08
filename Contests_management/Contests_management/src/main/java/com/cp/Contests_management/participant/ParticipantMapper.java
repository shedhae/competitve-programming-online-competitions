package com.cp.Contests_management.participant;

import com.cp.Contests_management.user.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
/*
    this SERVICE is capable of mapping from :
    participantDTO ----> Participant (to persist it in the db)
    participant    ----> ParticipantRESPONSEDTO (to be sent to the frontEnd)

 */
@Service
public class ParticipantMapper {
    private final UserMapper userMapper;

    public ParticipantMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public Participant dtoToParticipant(ParticipantDto participantDto){
        if(participantDto == null) {
            throw new IllegalArgumentException("ParticipantDto cannot be null");
        }
        Participant participant = new Participant();
        participant.setName(participantDto.name());
        return participant;
    }


    public ParticipantResponseDto participantToParticipantResponseDto(Participant participant){
        if(participant == null) {
            throw new IllegalArgumentException("Participant cannot be null");
        }
        if(participant.getUserCount() == 0 || participant.getUserCount() > 3) {
            throw new IllegalArgumentException("Number of users within the Participant Team  must be between 0 and 3");
        }

        return  new ParticipantResponseDto(
                participant.getName(),
                participant.getUsers()
                        .stream()
                        .map(userMapper::userToUserResponseDto)
                        .collect(Collectors.toList())
        );
    }
}
