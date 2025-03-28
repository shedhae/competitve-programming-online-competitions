package com.cp.Contests_management.participant;

import com.cp.Contests_management.user.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@Service
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final ParticipantMapper participantMapper;

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public ParticipantService(
            ParticipantRepository participantRepository,
            ParticipantMapper participantMapper,
            UserMapper userMapper,
            UserRepository userRepository
    ) {
        this.participantRepository = participantRepository;
        this.participantMapper = participantMapper;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }


    public ParticipantResponseDto createParticipant(
            Integer userId,
            ParticipantDto participantDto
    ){
        User user = userRepository.findById(userId).orElse(null);
        if(user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        Participant participant = participantMapper.dtoToParticipant(participantDto);




        //check if im trying to create an existing participant
        if(participantRepository.existsByName(participantDto.name())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Participant already exists");
        }

        //if the participant didn't provide a name assign the name of the creator
        if(participantDto.name() == null || participantDto.name().isEmpty()) {
            participant.setName(user.getName());
        }

        //initialize the users' list AND
        // assign the creator to the list of participants
        if(participant.getUsers()==null) {
            participant.setUsers(new ArrayList<>());
        }
        participant.getUsers().add(user);

        participantRepository.save(participant);
        return participantMapper.participantToParticipantResponseDto(participant);
    }
}
