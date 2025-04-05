package com.cp.Contests_management.submission;

import java.time.LocalDateTime;

import com.cp.Contests_management.Problem.Problem;
import com.cp.Contests_management.participant.Participant;

public record submissionDto(
    Integer id,
    LocalDateTime time,
    String language,
    String judgment,
    String code
   
) {}
