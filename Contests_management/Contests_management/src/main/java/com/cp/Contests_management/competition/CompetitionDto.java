package com.cp.Contests_management.competition;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
/*
    following codeforces naming standards of competitions
    eg: Div2 (minimum size is 4 )
 */
public record CompetitionDto(
        @NotEmpty(message = "You must provide a competition name")
        @Size(min = 4,message = "Competition name size must be at least containing 4 characters")
        String name,


        @Min(value = 30,message = "Competition duration must be at least 30 minutes long")
        float duration,

        @NotNull(message = "You must provide starting time for the competition")
        LocalDateTime startTime,


        float penaltyTime
) {
}
