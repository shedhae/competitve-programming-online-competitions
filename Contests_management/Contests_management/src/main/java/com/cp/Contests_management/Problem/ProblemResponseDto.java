package com.cp.Contests_management.Problem;



public record ProblemResponseDto( 
    String title,
    String Competition,
    String Creator,
    char label,
    String content,
    float timelimit,
    float memorylimit ,
    Integer rating

) {



    
}
