package com.cp.Contests_management.Problem;

public record ProblemResponseDto( 
    String title,
    String Competion,
    String Creator,
    char label,
    String content,
    float timelimit,
    float memorylimit

) {}
