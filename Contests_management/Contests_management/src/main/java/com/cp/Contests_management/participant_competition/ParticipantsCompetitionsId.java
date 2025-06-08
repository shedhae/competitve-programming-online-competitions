package com.cp.Contests_management.participant_competition;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@Embeddable
public class ParticipantsCompetitionsId implements Serializable {

    private Integer participantId;
    private Integer competitionId;

    // Default constructor (required by JPA)
    public ParticipantsCompetitionsId() {}

    // Parameterized constructor
    public ParticipantsCompetitionsId(Integer participantId, Integer competitionId) {
        this.participantId = participantId;
        this.competitionId = competitionId;
    }

    // Explicitly implement equals() and hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipantsCompetitionsId that = (ParticipantsCompetitionsId) o;
        return Objects.equals(participantId, that.participantId) &&
                Objects.equals(competitionId, that.competitionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(participantId, competitionId);
    }
}
