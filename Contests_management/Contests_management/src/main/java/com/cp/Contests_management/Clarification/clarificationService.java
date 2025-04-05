package com.cp.Contests_management.Clarification;

import com.cp.Contests_management.Problem.Problem;
import com.cp.Contests_management.Problem.ProblemRepository;
import com.cp.Contests_management.participant.Participant;
import com.cp.Contests_management.participant.ParticipantRepository;

import org.springframework.stereotype.Service;

import java.util.List;

import java.util.stream.Collectors;

@Service
public class clarificationService {

    private final clarificationRepository clarificationRepository;
    
    private final clarificationMapper clarificationMapper;

    private ProblemRepository problemRepository;

    private ParticipantRepository participantRepository;

    public clarificationService(clarificationRepository clarificationRepository,
                                ParticipantRepository participantRepository,
                                ProblemRepository problemRepository,
                                clarificationMapper clarificationMapper) {
        this.clarificationRepository = clarificationRepository;
         this.clarificationMapper = clarificationMapper;
        this.problemRepository = problemRepository;
        this.participantRepository = participantRepository;

    }

    public clarificationResponseDto createClarification(clarificationDto clarificationDto,Integer problemId,Integer participantId) {

         if(clarificationDto==null)
        throw new IllegalArgumentException("clarificationDto cannot be null");


        if(participantId==null)
            throw new IllegalArgumentException("participantId cannot be null");

        if(problemId==null)
            throw new IllegalArgumentException("problemId cannot be null");

        Problem problem = problemRepository.findById(problemId).orElse(null);
        if(problem==null)
        throw new IllegalArgumentException("problem does not exist");
         
        Participant participant = participantRepository.findById(participantId).orElse(null);
        if(participant==null)
        throw new IllegalArgumentException("participant does not exist");

         clarification clarification = clarificationMapper.dtoToClarification(clarificationDto);
         clarification.setProblem(problem);
         clarification.setParticipant(participant);

        clarification savedClarification = clarificationRepository.save(clarification);
        return clarificationMapper.clarificationToResponseDto(savedClarification);
    }

    public List<clarificationResponseDto> getClarificationsByProblem(Integer problemId) {
        return clarificationRepository.findByProblemId(problemId)
                .stream()
                .map(clarificationMapper::clarificationToResponseDto)
                .collect(Collectors.toList());
    }

    public clarificationResponseDto getClarificationById(Integer clarificationId) {

        if(clarificationId==null)
        throw new IllegalArgumentException("clarificationId cannot be null");

         clarification clarification= clarificationRepository.findById(clarificationId).orElse(null);
         if (clarification == null)
         throw new IllegalArgumentException("clarification does not exist");

     return clarificationMapper.clarificationToResponseDto(clarification);
               
    }
     

    public clarificationResponseDto updateClarification(Integer clarificationId, clarificationDto clarificationDto) {

        if (clarificationId == null || clarificationDto == null)
            throw new IllegalArgumentException("clarificationId and clarificationDto cannot be null");

        clarification clarification = clarificationRepository.findById(clarificationId).orElse(null);
        if (clarification == null)
            throw new IllegalArgumentException("clarification does not exist");
          
        
        clarification.setContent((clarificationDto.content()));
        clarification savedclarification = clarificationRepository.save(clarification);

         return  clarificationMapper.clarificationToResponseDto(savedclarification); }
   

         public void deleteClarification(Integer clarificationId) {
            if (clarificationId == null)
                throw new IllegalArgumentException("clarificationId cannot be null");
    
            clarification clarification = clarificationRepository.findById(clarificationId).orElse(null);
            if (clarification == null)
                throw new IllegalArgumentException("clarification does not exist");
    
            clarificationRepository.deleteById(clarificationId);
        }

     

}
