package com.cp.Contests_management.competition;

import com.cp.Contests_management.user.User;
import org.springframework.stereotype.Service;

@Service
public class CompetitionMapper {
    public Competition dtoToCompetition(CompetitionDto dto) {
        if(dto == null) {
            throw new IllegalArgumentException("Competition DTO cannot be null");
        }
        Competition competition = new Competition();
        competition.setName(dto.name());
        competition.setDuration(dto.duration());
        competition.setPenaltyTime(dto.penaltyTime());
        competition.setStartTime(dto.startTime());
        return competition;
    }

    public CompetitionResponseDto competitionToResponseDto(Competition competition) {

        if(competition == null) {
            throw new IllegalArgumentException("Competition cannot be null");
        }
        return new CompetitionResponseDto(
                competition.getName(),
                competition.getUser().getName(),
                competition.getStartTime(),
                competition.getEndTime(),
                competition.getDuration(),
                competition.getPenaltyTime()
        );
    }
}
