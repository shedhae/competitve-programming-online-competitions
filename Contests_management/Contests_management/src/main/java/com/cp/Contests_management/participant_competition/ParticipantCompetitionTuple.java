package com.cp.Contests_management.participant_competition;

import com.cp.Contests_management.competition.Competition;
import com.cp.Contests_management.participant.Participant;

public record ParticipantCompetitionTuple(
        boolean exists,
        Participant participant,
        Competition competition
) {}
