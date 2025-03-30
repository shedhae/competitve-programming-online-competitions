package com.cp.Contests_management.participant;


import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
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
            @Valid @RequestBody ParticipantDto participantDto
    ){
        return participantService
                .createParticipant(
                    userId,
                    participantDto
                );
    }

    @GetMapping("/participants/{participant_id}")
    public ParticipantResponseDto getParticipantById(
            @PathVariable("participant_id") Integer participantId
    ){
        return participantService.getParticipantById(participantId);
    }
    @GetMapping("/participants/{participant_name}")
    public ParticipantResponseDto getParticipantByName(
            @PathVariable("participant_name") String participantName
    ){
        return participantService.getParticipantByName(participantName);
    }
    @GetMapping("/participants")
    public List<ParticipantResponseDto> getAllParticipants(){
        return participantService.getAllParticipants();
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
    @Transactional
    @DeleteMapping("/participants/{participant_id}")
    public void deleteParticipantById(
            @PathVariable("participant_id") Integer participantId
    ){
        participantService.deleteParticipantById(participantId);
    }
    /*
            Delete existing user from a participant team.

            If the participant team is only formed
            by one user that who has created it by default
            don't delete the user from the team.

            If the participant team contains no users
            Delete the participant team from the database.
     */
    @Transactional
    @DeleteMapping("/participants/delete-user/{user_id}/{participant_id}")
    public ParticipantResponseDto deleteUserFromParticipant(
            @PathVariable("user_id") Integer userId,
            @PathVariable("participant_id") Integer participantId
    ){
        return participantService.deleteUserFromParticipants(userId,participantId);
    }
}
