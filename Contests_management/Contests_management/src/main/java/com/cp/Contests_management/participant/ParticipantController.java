package com.cp.Contests_management.participant;

import com.cp.Contests_management.user.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class ParticipantController {
    private final ParticipantService participantService;

    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }


    @PostMapping("/participants")
    public ParticipantResponseDto createParticipant(
            @RequestParam Integer userId,
            @RequestBody ParticipantDto participantDto
    ){
        return participantService
                .createParticipant(
                    userId,
                    participantDto
                );
    }


    //Patch is used because I want only to add a new user to the list
    //instead of updating all the list elements
    @PatchMapping("/participants/addUserToParticipants/{user_id}/{participant_id}")
    public ParticipantResponseDto addUserToParticipants(
            @PathVariable("user_id") Integer userId,
            @PathVariable("participant_id") Integer participantId
    ){
        return participantService
                .addUserToParticipants(
                        userId,
                        participantId
                );
    }


    @GetMapping("/participants/{participant_id}")
    public ParticipantResponseDto getParticipantById(
            @PathVariable("participant_id") Integer participantId
    ){
        return participantService.getParticipantById(participantId);
    }

    @GetMapping("/participants")
    public List<ParticipantResponseDto> getAllParticipants(){
        return participantService.getAllParticipants();
    }


    @DeleteMapping("/participants/{participant_id}")
    public void deleteParticipantById(
            @PathVariable("participant_id") Integer participantId
    ){
        participantService.deleteParticipantById(participantId);
    }

    @Transactional
    @DeleteMapping("/participants/delete-user")
    public ParticipantResponseDto deleteUserFromParticipant(
            @RequestParam Integer userId,
            @RequestBody ParticipantDto participantDto
    ){
        return participantService.deleteUserFromParticipants(userId, participantDto);
    }








}
