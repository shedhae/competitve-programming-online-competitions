package com.cp.Contests_management.competition;

import java.time.LocalDateTime;

public record CompetitionResponseDto (
    String name,
    String creatorName,
    LocalDateTime startingTime,
    LocalDateTime endingTime,
    float duration,
    float penaltyTime
){
}
