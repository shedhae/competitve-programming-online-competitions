package com.cp.Contests_management.participant_competition;

import com.cp.Contests_management.competition.Competition;
import com.cp.Contests_management.participant.Participant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
    Composite id <participantId, competitionId>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ParticipantCompetition {
    @EmbeddedId
    private ParticipantsCompetitionsId id;

    private int score = 0;
    private int rank;

    @ManyToOne
    @JoinColumn(name = "participant_id", nullable = false)
    @MapsId("participantId")
    private Participant participant;

    @ManyToOne
    @JoinColumn(name = "competition_id", nullable = false)
    @MapsId("competitionId")
    private Competition competition;


}
