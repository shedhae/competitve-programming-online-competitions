package com.cp.Contests_management.user;

import com.cp.Contests_management.participant.*;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ParticipantService participantService;
    private final ParticipantMapper participantMapper;
    private final ParticipantRepository participantRepository;

    public UserService(UserRepository userRepository, UserMapper userMapper, ParticipantService participantService, ParticipantMapper participantMapper, ParticipantRepository participantRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.participantService = participantService;
        this.participantMapper = participantMapper;
        this.participantRepository = participantRepository;
    }

    /*
        By Default while creating a user
        we will create a Participant
        Having the same name of the user.

     */

    public UserResponseDto createUser(UserDto userDto){

        if (userRepository.existsByEmail(userDto.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }else if(userRepository.existsByName(userDto.name())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Name already exists");
        }
        var user = userMapper.dtoToUser(userDto);
        var savedUser = userRepository.save(user);

        //Creating & persisting the participant element in the db
        ParticipantDto participantDto = new ParticipantDto(savedUser.getName());
        ParticipantResponseDto savedParticipantDto = participantService.createParticipant(savedUser.getId(), participantDto);
        Participant savedParticipant = participantRepository.findByName((savedParticipantDto.name()));

        //update the list of participants of the user
        if(savedUser.getParticipations() == null)
            savedUser.setParticipations(new ArrayList<>());
        savedUser.getParticipations().add(savedParticipant);

        //Updating the user (because we changed its list of participants )
        userRepository.save(savedUser);

        return userMapper.userToUserResponseDto(savedUser);
    }


    public List<UserResponseDto> getAllUsers(){
        java.util.List<User> users = userRepository.findAll();
        return users
                .stream()
                .map(userMapper::userToUserResponseDto)
                .collect(Collectors.toList());
    }
    public UserResponseDto getUserById(Integer userId) {
        return userMapper.userToUserResponseDto(
                userRepository.findById(userId)
                        .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"))
        );
    }
    public List<UserResponseDto> getUserByName(String userName){
        List<User> users = userRepository.findByNameContaining(userName);
        if (users.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No users found with name: " + userName);
        }
        return users
                .stream()
                .map(userMapper::userToUserResponseDto)
                .collect(Collectors.toList());
    }

    /*
        After deleting the user within the database
        we have to delete him
        from all the participants list.
        If the participant contains only that user
        we have to deleted completely from the database
    */
    @Transactional
    public void deleteUserById(Integer userId){
        User deletedUser = userRepository.findById(userId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User already doesn't exist"));

        List<Participant> participantsOfDeletedUser = deletedUser.getParticipations();

        for(Participant participant : participantsOfDeletedUser){
            participant.getUsers().remove(deletedUser);
            if(participant.getUsers().isEmpty())
                participantRepository.delete(participant);
        }

        userRepository.delete(deletedUser);
    }
    @Transactional
    public void deleteUserByName( String userName){

        if (!userRepository.existsByName(userName)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Name not found");
        }
        User deletedUser = userRepository.findByName(userName);

        List<Participant> participantsOfDeletedUser = deletedUser.getParticipations();

        for(Participant participant : participantsOfDeletedUser){
            participant.getUsers().remove(deletedUser);
            if(participant.getUsers().isEmpty())
                participantRepository.delete(participant);
        }
        userRepository.delete(deletedUser);

    }

    public List<ParticipantResponseDto> getAllUserParticipants(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return user.getParticipations().stream()
                .map(participantMapper::participantToParticipantResponseDto)
                .collect(Collectors.toList());
    }
}
