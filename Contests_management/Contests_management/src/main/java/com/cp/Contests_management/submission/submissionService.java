package com.cp.Contests_management.submission;
import com.cp.Contests_management.Problem.Problem;
import com.cp.Contests_management.Problem.ProblemRepository;
import com.cp.Contests_management.participant.Participant;
import com.cp.Contests_management.participant.ParticipantRepository;

import org.springframework.stereotype.Service;

@Service
public class submissionService {

    private final submissionRepository submissionRepository;
    private final ProblemRepository problemRepository;
    private final ParticipantRepository participantRepository;
    private final submissionMapper submissionMapper;

    public submissionService(submissionRepository submissionRepository, 
                             ProblemRepository problemRepository, 
                             ParticipantRepository participantRepository,
                             submissionMapper submissionMapper) {
        this.submissionRepository = submissionRepository;
        this.problemRepository = problemRepository;
        this.participantRepository = participantRepository;
        this.submissionMapper = submissionMapper;
    }

    
    public submissionResponseDto createSubmission(submissionDto submissionDto,Integer problemId,Integer participantId) {
        if(submissionDto==null)
        throw new IllegalArgumentException("submissionDto cannot be null");


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

        
        submission submission = submissionMapper.dtoToSubmission(submissionDto);
        submission.setProblem(problem);
        submission.setParticipant(participant);

        
        submission savedSubmission = submissionRepository.save(submission);
        return submissionMapper.submissiontoResponseDto(savedSubmission);
    }

   

    public submissionResponseDto getSubmissionById(Integer submissionId) {
        submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found"));
        return submissionMapper.submissiontoResponseDto(submission);
    }

    
}