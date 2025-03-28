package com.cp.Contests_management.participant;

import com.cp.Contests_management.user.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ParticipantController {
    private final ParticipantService participantService;

    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    //Create methods
    @PostMapping("/participants")
    public ParticipantResponseDto createParticipant(
            @Valid @RequestParam Integer userId,
            @RequestBody ParticipantDto participantDto
    ){
        return participantService
                .createParticipant(
                    userId,
                    participantDto
                );
    }

    //Add a user to an existing Participant team
    //Patch is used because I want only to add a new user to the list
    //instead of updating all the list elements
    @PatchMapping("participants/addUserToParticipants/{user_id}/{participant_id}")
    public ParticipantResponseDto addUserToParticpants(
            @PathVariable("user_id") Integer userId,
            @PathVariable("participant_id") Integer participantId
    ){
        return participantService
                .addUserToParticpants(
                        userId,
                        participantId
                );
    }






}
