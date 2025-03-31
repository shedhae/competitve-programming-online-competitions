package com.cp.Contests_management.participant_competition;

public record ParticipantCompetitionResponseDto(
        String participantName,
        String competitionName,
        int rank
) {
}
