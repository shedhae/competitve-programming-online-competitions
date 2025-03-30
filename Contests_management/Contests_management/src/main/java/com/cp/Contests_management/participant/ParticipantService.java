package com.cp.Contests_management.participant;

import com.cp.Contests_management.user.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final ParticipantMapper participantMapper;
    private final UserRepository userRepository;

    public ParticipantService(
            ParticipantRepository participantRepository,
            ParticipantMapper participantMapper,
            UserRepository userRepository
    ) {
        this.participantRepository = participantRepository;
        this.participantMapper = participantMapper;
        this.userRepository = userRepository;
    }


    /*
        Check if im trying to create an existing participant.

        If the name field in the ParticipantDto object is null
        Than name the participant with the same name as the user.

        Add the user object to the participant users list
     */
    public ParticipantResponseDto createParticipant(
            Integer userId,
            ParticipantDto participantDto
    ){
        User user = userRepository.findById(userId).orElse(null);
        if(user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        Participant participant = participantMapper.dtoToParticipant(participantDto);
        if(participantRepository.existsByName(participantDto.name())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Participant already exists");
        }
        if(participantDto.name() == null || participantDto.name().isEmpty()) {
            participant.setName(user.getName());
        }
        if(participant.getUsers()==null) {
            participant.setUsers(new ArrayList<>());
        }
        participant.getUsers().add(user);
        participantRepository.save(participant);
        return participantMapper.participantToParticipantResponseDto(participant);
    }

    public ParticipantResponseDto getParticipantById(Integer participantId) {
        var participant = participantRepository.findById(participantId).orElse(null);
        if(participant == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Participant not found");
        }else{
            return participantMapper.participantToParticipantResponseDto(participant);
        }
    }
    public ParticipantResponseDto getParticipantByName(String participantName) {
        Participant participant = participantRepository.findByName(participantName);
        if(participant == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Participant not found");
        }
        return participantMapper.participantToParticipantResponseDto(participant);
    }
    public List<ParticipantResponseDto> getAllParticipants() {
        List<Participant> participants = participantRepository.findAll();
        return participants
                .stream()
                .map(participantMapper::participantToParticipantResponseDto)
                .collect(Collectors.toList());
    }

    /*
        Delete a participant team
        the participant team's name to be deleted
        must not have same name of a user
        because this is a default Participant team
        created to submit problems with single user.
     */
    public void deleteParticipantById(Integer participantId) {
        Participant participant = participantRepository.findById(participantId).orElse(null);
        if(participant == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Participant not found");
        }

        if(participant.getUsers().size()==1) {
            User user = participant.getUsers().getFirst();
            if(user.getName().equals(participant.getName())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot delete default participant team");
            }
        }
        participantRepository.delete(participant);
    }

    /*
        Adding new user to participant team
        with maintaining the number of teammates
        between 1 and 3.
        Prevent adding User to a default Participant of another user
     */
    public ParticipantResponseDto addUserToParticipants(
             Integer UserId,
             Integer participantId
    ){
        var participant = participantRepository.findById(participantId).orElse(null);
        if(participant == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Participant team not found");
        }
        var user = userRepository.findById(UserId).orElse(null);
        if(user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        if(participant.getUsers().contains(user)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists within the participants ");
        }
        if(participant.getUsers().size()==3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Participant team is FULL! ");
        }

        String participantName = participant.getName();
        if(userRepository.existsByName(participantName)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot add user to default participant of another user");
        }

        participant.getUsers().add(user);

        participantRepository.save(participant);
        return participantMapper.participantToParticipantResponseDto(participant);
    }


    /*
        Delete existing user from a participant team.

        If the participant team is only formed
        by one user that who has created it by default
        don't delete the user from the team.

        If the participant team contains no users
        Delete the participant team from the database.
    */
    public ParticipantResponseDto  deleteUserFromParticipants(
            Integer userId,
            Integer ParticipantId

    ){
        Participant participant = participantRepository.findById(ParticipantId).orElse(null);
        if(participant == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Participant not found");
        }
        User user = userRepository.findById(userId).orElse(null);
        if(user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        if(!participant.getUsers().contains(user)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User  does not exist within the participants");
        }
        if(participant.getUsers().size()==1 && participant.getUsers().contains(user) && participant.getName().equals(user.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot delete default user team");
        }
        participant.getUsers().remove(user);
        if(participant.getUsers().isEmpty()) {
            participantRepository.delete(participant);
            return null;
        }
        return participantMapper.participantToParticipantResponseDto(participantRepository.save(participant));
    }

}
