package com.cp.Contests_management.Problem;
import com.cp.Contests_management.competition.Competition;
import com.cp.Contests_management.competition.CompetitionDto;
import com.cp.Contests_management.competition.CompetitionRepository;
import com.cp.Contests_management.competition.CompetitionResponseDto;
import com.cp.Contests_management.user.User;
import com.cp.Contests_management.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
    public class ProblemService {

        private ProblemRepository problemRepository;
        private ProblemMapper problemMapper;
        private final UserRepository userRepository;
        private      CompetitionRepository competitionRepository;
        
                
     public ProblemService(ProblemRepository problemRepository, ProblemMapper problemMapper,UserRepository userRepository, CompetitionRepository  competitionRepository) 
            {   
                this.competitionRepository = competitionRepository;
                this.problemRepository     = problemRepository;
                this.problemMapper         = problemMapper;
                this.userRepository        = userRepository;
             } 



     //Create a Problem
    public ProblemResponseDto createProblem (Integer userId , ProblemDto problemDto , Integer competitionId ){
       
        if(userId==null)
            throw new IllegalArgumentException("userId cannot be null");

        if(competitionId==null)
            throw new IllegalArgumentException("competitionId cannot be null");

        if(problemDto==null)
            throw new IllegalArgumentException("problemDto cannot be null");

        User user = userRepository.findById(userId).orElse(null);
        if(user==null)
            throw new IllegalArgumentException("user does not exist");
        
        Competition competiton =competitionRepository.findById(competitionId).orElse(null);
        if(competiton==null)
            throw new IllegalArgumentException("competition does not exist");
        
        
        Problem problem = problemMapper.dtoToProblem(problemDto);
        
        problem.setCompetition(competiton);
        problem.setCreator(user);

        Problem savedProblem = problemRepository.save(problem);
        
        ProblemResponseDto problemResponseDto =problemMapper.ProblemtoResponseDto(savedProblem);
        
        return problemResponseDto;

     }


    //find a problem by ID 

    public ProblemResponseDto getProblemById(Integer problemId) {
        Problem problem = problemRepository.findById(problemId).orElse(null);
        if (problem == null)
            throw new IllegalArgumentException("competition does not exist");
        return problemMapper.ProblemtoResponseDto(problem);
    }
       
    // get all problems of a competition 

    public List<ProblemResponseDto> getProblemByCompetition(Integer competitionId){
        
        Competition competiton =competitionRepository.findById(competitionId).orElse(null);
        if(competiton==null)
            throw new IllegalArgumentException("user does not exist");


        
        List<ProblemResponseDto> problems = problemRepository.findProblemByCompetition(competiton)
        .stream()
        .map(problemMapper::ProblemtoResponseDto)
        .collect(Collectors.toList());
        
        return problems ;
    }

   //get problems by creator 

   public List<ProblemResponseDto> getProblemsByCreator(Integer userId) {
    if (userId == null) {
        throw new IllegalArgumentException("User ID cannot be null");
    }

    User user = userRepository.findById(userId) .orElseThrow(() -> new RuntimeException("User not found!"));
       
    

    return problemRepository.findProblemByCreator(user)
            .stream()
            .map(problemMapper::ProblemtoResponseDto)
            .collect(Collectors.toList());
}


 // get problem by name 

    public ProblemResponseDto getProblemByName(String name) {
        Problem problem = problemRepository.findByName(name);
        if(problem == null)
            throw new IllegalArgumentException("problem does not exist");
        return problemMapper.ProblemtoResponseDto(problem);
    }
     
//update problem 

  public ProblemResponseDto updateProblemById(Integer problemId, ProblemDto problemDto) {
    Problem problem = problemRepository.findById(problemId).orElse(null);
        if(problem==null)
            throw new IllegalArgumentException("problem does not exist");

         // ensure the given name is not already in use
        if(problemRepository.existsByName(problemDto.title()))
            throw new IllegalArgumentException("This name is already in use");

           problem.setTitle(problemDto.title());
           problem.setContent(problemDto.content());
           problem.setMemorylimit(problemDto.memorylimit());
           problem.setTimelimit(problemDto.timelimit());
           

         problemRepository.save(problem);

         return problemMapper.ProblemtoResponseDto(problem); }

//add problem to competition 
public ProblemResponseDto addProblemToCompetition(Integer problemId, Integer competitionId) {
   
    if (problemId == null || competitionId == null) {
        throw new IllegalArgumentException("Problem ID and Competition ID cannot be null");
    }

     Problem problem = problemRepository.findById(problemId)
            .orElseThrow(() -> new RuntimeException("Problem not found!"));

     Competition competition = competitionRepository.findById(competitionId)
            .orElseThrow(() -> new RuntimeException("Competition not found!"));

     //ensure that the problem doesn't already exist in the competition
   
    if (problemRepository.findProblemByCompetition(competition).contains(problem) ) {
        throw new IllegalArgumentException("Problem already exisit in competition");
    }            

   

     problem.setCompetition(competition);

     Problem savedProblem = problemRepository.save(problem);

 return problemMapper.ProblemtoResponseDto(savedProblem);
}



//delete from competition 
public void removeProblemFromCompetition(Integer problemId) {
 
    if (problemId == null) {
        throw new IllegalArgumentException("Problem ID cannot be null");
    }
    Problem problem = problemRepository.findById(problemId)
            .orElseThrow(() -> new RuntimeException("Problem not found!"));

    
    LocalDateTime now = LocalDateTime.now();

    // Ensure the current date is before the competition's start time and competition's end time
    if (now.isAfter(problem.getCompetition().getStartTime()) ) {
        throw new RuntimeException("Cannot remove problem: Competition has already started.");
    }

    if (now.isAfter(problem.getCompetition().getEndTime()) ) {
        throw new RuntimeException("Cannot remove problem: Competition has already ended.");
    }
     
  
    problemRepository.deleteById(problemId);

}



//update rating of problem after competition 
public ProblemResponseDto updateProblemRating(Integer problemId, Integer rating) {
    if (problemId == null) {
        throw new IllegalArgumentException("Problem ID cannot be null");
    }
    Problem problem = problemRepository.findById(problemId).orElse(null);
        if(problem==null)
            throw new IllegalArgumentException("problem does not exist");
        
     // Ensure the current date is before the competition's start time and competition's end time
       
     LocalDateTime now = LocalDateTime.now();
     if (now.isAfter(problem.getCompetition().getEndTime()) ) 
       
        {problem.setRating(rating);
        problemRepository.save(problem); }
     else 
     throw new RuntimeException("Cannot update the problem's rating unless the competition ends ");
    

    return problemMapper.ProblemtoResponseDto(problem); }
  

}
