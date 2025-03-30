package com.cp.Contests_management.competition;

import com.cp.Contests_management.user.User;
import com.cp.Contests_management.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CompetitionService {
    private final CompetitionRepository competitionRepository;
    private final CompetitionMapper competitionMapper;
    private final UserRepository userRepository;

    public CompetitionService(CompetitionRepository competitionRepository, CompetitionMapper competitionMapper, UserRepository userRepository) {
        this.competitionRepository = competitionRepository;
        this.competitionMapper = competitionMapper;
        this.userRepository = userRepository;
    }

    public CompetitionResponseDto createCompetition(Integer userId, CompetitionDto competitionDto) {
        if(userId==null)
            throw new IllegalArgumentException("userId cannot be null");
        if(competitionDto==null)
            throw new IllegalArgumentException("competitionDto cannot be null");
        User user = userRepository.findById(userId).orElse(null);
        if(user==null)
            throw new IllegalArgumentException("user does not exist");
        Competition competition = competitionMapper.dtoToCompetition(competitionDto);
        competition.setUser(user);
        Competition savedCompetition =  competitionRepository.save(competition);
        return competitionMapper.competitionToResponseDto(savedCompetition);

    }
}
