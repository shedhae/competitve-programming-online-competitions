package com.cp.Contests_management.Problem;

import com.cp.Contests_management.competition.Competition;
import com.cp.Contests_management.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Problem {
    @Id
    @GeneratedValue
    private Integer id;


    @Column(
            nullable = false,
            unique = true
    )
    private String title;

    @Column(
            unique = true
    )
    @NotBlank(message = "label must not be empty")
    @Size(min = 1, max = 1 ,message = "size of label must not exceed 1")
    private char label;

    @Column( nullable = false)
    private String content;

    @Column( nullable = false)
    private float timelimit;

    @Column( nullable = false)
     private float memorylimit;

   // Many problems can belong to one competition
    @ManyToOne
    @JoinColumn(name = "competition_id", nullable = false)
    private Competition competition;

    @Column( nullable = false)
     private User creator ;

     @Column(nullable = true)
    private Integer rating ;  

}