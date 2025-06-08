package com.cp.Contests_management.Clarification;

import com.cp.Contests_management.participant.Participant;
import com.cp.Contests_management.Problem.Problem;
import org.springframework.stereotype.Component;

@Component
public class clarificationMapper {

    public clarification dtoToClarification(clarificationDto clarificationdto) {

        if(clarificationdto == null) {
            throw new IllegalArgumentException("clarificationDto cannot be null");
        }

       clarification  clarification = new clarification();
       clarification.setContent(clarificationdto.content());
          return clarification ;


    }

      
    public clarificationResponseDto clarificationToResponseDto(clarification clarification) {
       
        if(clarification == null) {
            throw new IllegalArgumentException("clarification cannot be null");
        }
     return new clarificationResponseDto(clarification.getContent());
    }
}