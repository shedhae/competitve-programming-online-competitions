package com.cp.Contests_management.participant;

import com.cp.Contests_management.user.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

}
