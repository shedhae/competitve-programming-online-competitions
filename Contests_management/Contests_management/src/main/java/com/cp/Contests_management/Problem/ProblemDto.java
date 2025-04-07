package com.cp.Contests_management.Problem;

import com.cp.Contests_management.competition.Competition;
import com.cp.Contests_management.user.User;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ProblemDto(  
     @NotEmpty(message = "You must provide a competition name")
     String title,

     char label,

     @NotNull(message = "You must provide content for the Problem")
     String content,

     @Max(value = 3,message = "Maximum of timelimit of a problem shouldn't depass 3sec ") 
     float timelimit,
     
     @Max(value = 1,message = "Maximum of memoryLimit of a problem shouldn't depass 1GB ") 
      float memorylimit      
      
      )
 {}
