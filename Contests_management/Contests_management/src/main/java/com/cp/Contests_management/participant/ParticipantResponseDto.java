package com.cp.Contests_management.participant;

import com.cp.Contests_management.user.UserDto;
import com.cp.Contests_management.user.UserResponseDto;

import java.util.List;

/*
    in the response  always return
    the team name accompanied
    by the participants
 */
public record ParticipantResponseDto(
        String name,
        List<UserResponseDto> participants
) {
}
