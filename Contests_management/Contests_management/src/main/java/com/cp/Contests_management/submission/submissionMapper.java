package com.cp.Contests_management.submission;

import org.springframework.stereotype.Service;

import com.cp.Contests_management.Problem.ProblemResponseDto;

@Service

public class submissionMapper {

 
    public submission dtoToSubmission(submissionDto submissionDto){
    
        if(submissionDto == null) {
            throw new IllegalArgumentException("submissionDto can't be null");
        }
        
        submission submission = new submission();
        submission.setLanguage(submissionDto.language());
        submission.setJudgment(submissionDto.judgment());
        submission.setCode(submissionDto.code());
       

        return submission;
       }


    public submissionResponseDto submissiontoResponseDto(submission submission) {

    if(submission== null) {
        throw new IllegalArgumentException("submission cannot be null");
    }

    submissionResponseDto submissionResponseDto = new submissionResponseDto(
        submission.getTime(),
        submission.getLanguage(),
        submission.getJudgment(),
        submission.getCode(),
        submission.getProblem().getTitle() 
    );


        return  submissionResponseDto ; }
    
}
