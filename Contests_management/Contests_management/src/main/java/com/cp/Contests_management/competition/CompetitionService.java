package com.cp.Contests_management.competition;

import com.cp.Contests_management.user.User;
import com.cp.Contests_management.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CompetitionService {

    private final CompetitionRepository competitionRepository;
    private final CompetitionMapper competitionMapper;
    private final UserRepository userRepository;

    public CompetitionService(
            CompetitionRepository competitionRepository,
            CompetitionMapper competitionMapper,
            UserRepository userRepository
    )
    {
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

    public List<CompetitionResponseDto> getAllCompetitions(){
        List<Competition> competitions = competitionRepository.findAll();
        return competitions
                .stream()
                .map(competitionMapper::competitionToResponseDto)
                .collect(Collectors.toList());
    }

    public CompetitionResponseDto getCompetitionById(Integer competitionId) {
        Competition competition = competitionRepository.findById(competitionId).orElse(null);
        if (competition == null)
            throw new IllegalArgumentException("competition does not exist");
        return competitionMapper.competitionToResponseDto(competition);
    }
    public CompetitionResponseDto getCompetitionByName(String name) {
        Competition competition = competitionRepository.findByName(name);
        if(competition == null)
            throw new IllegalArgumentException("competition does not exist");
        return competitionMapper.competitionToResponseDto(competition);
    }


    public CompetitionResponseDto updateCompetitionById(Integer competitionId, CompetitionDto competitionDto) {
        Competition competition = competitionRepository.findById(competitionId).orElse(null);
        if(competition==null)
            throw new IllegalArgumentException("competition does not exist");

        if(!Objects.equals(competitionDto.name(), competition.getName())
                && competitionRepository.existsByName(competitionDto.name()))
            throw new IllegalArgumentException("This name is already in use");

        //the following fields are not empty by default
        competition.setName(competitionDto.name());
        competition.setDuration(competitionDto.duration());
        competition.setStartTime(competitionDto.startTime());
        if (competitionDto.penaltyTime() != 0)
            competition.setPenaltyTime(competitionDto.penaltyTime());

        competition.setEndTime();
        competitionRepository.save(competition);
        return competitionMapper.competitionToResponseDto(competition);

    }
}
