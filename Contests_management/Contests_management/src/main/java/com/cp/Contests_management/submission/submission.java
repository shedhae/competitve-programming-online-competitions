package com.cp.Contests_management.submission;

import com.cp.Contests_management.Problem.Problem;
import com.cp.Contests_management.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import  com.cp.Contests_management.participant.Participant;


import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor


public class submission {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDateTime time = LocalDateTime.now();

    @Column(nullable = false)
    @Pattern(regexp = "ada|c|cpp|csharp|go|haskell|java|javascript|kotlin|objectivec|pascal|php|prolog|python2|python3|ruby|rust|scala",
    message = "Invalid programming language")
    private String language;

    @Column(nullable = false)
    @Pattern(regexp = "in_queue|accepted|wrong_answer|time_limit_exceeded|memory_limit_exceeded|compilation_error",
    message = "Invalid judgment type")
    private String judgment;

    @Column(nullable = false, length = 10000)
    @NotBlank(message = "Code cannot be empty")
    private String code;

    // Many submissions belong to one problem
    @ManyToOne
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;

    // Many submissions can be made by one user
    @ManyToOne
    @JoinColumn(name = "participant_id", nullable = false)
    private Participant participant ;
}

