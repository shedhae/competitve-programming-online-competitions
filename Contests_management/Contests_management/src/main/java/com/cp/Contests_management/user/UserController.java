package com.cp.Contests_management.user;

import com.cp.Contests_management.competition.CompetitionResponseDto;
import com.cp.Contests_management.participant.ParticipantResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto createUser(
            @Valid @RequestBody UserDto userDto
    ){
        return userService.createUser(userDto);
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponseDto> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/users/{user_id}")
    public UserResponseDto getUserById(
            @PathVariable("user_id") Integer userId
    ) {
        return userService.getUserById(userId);
    }

    @GetMapping("/users/search/{user_name}")
    public List<UserResponseDto> getUserByName(
            @PathVariable("user_name") String userName
    ){
        return userService.getUserByName(userName);

    }

    @DeleteMapping("/users/{user_id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserById(
            @PathVariable("user_id") Integer userId
    ){
        userService.deleteUserById(userId);
    }

    @DeleteMapping("/users/delete/{user_name}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserByName(
            @PathVariable("user_name") String userName
    ){
        userService.deleteUserByName(userName);
    }

    /*
        Get all the teams that the user has
        joined and the default participant object
     */
    @GetMapping("/users/participants/{user_id}")
    public List<ParticipantResponseDto> getAllUserParticipants(
            @PathVariable("user_id") Integer userId
    ){
        return userService.getAllUserParticipants(userId);
    }
    /*
        Get all the competitions that a certain user
        had created
     */
    @GetMapping("/users/competitions/{user_id}")
    public List<CompetitionResponseDto> getAllUserCompetitions(
            @PathVariable("user_id") Integer userId
    ){
        return userService.getAllUserCreatedCompetitions(userId);
    }

}
