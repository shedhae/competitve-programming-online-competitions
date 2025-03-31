package com.cp.Contests_management.participant_competition;

import jakarta.validation.constraints.NotEmpty;

public record ParticipantCompetitionDto(
        @NotEmpty(message = "You should provide the participant Name")
        String participantName,
        @NotEmpty(message = "You should provide the competition Name")
        String CompetitionName
) {
}
