package com.cp.Contests_management.competition;

import com.cp.Contests_management.participant_competition.ParticipantCompetition;
import com.cp.Contests_management.user.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Competition {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private float duration;  // Duration in minutes

    @Column(nullable = false)
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private float penaltyTime = 20;

    /*
        Here the user is the one
        who created the competition
     */
    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    @JsonManagedReference
    private User user;

    /*
        List of teams/user who
        participated in a competition
     */
    @OneToMany(mappedBy = "competition")
    private List<ParticipantCompetition> participantsCompetitions;


    /*
        Automatically update the endTime
        while creating an instance of
        the competition
     */
    @PrePersist
    @PreUpdate
    public void setEndTime() {
        if (startTime != null && duration > 0) {
            this.endTime = startTime.plusMinutes((long) duration);
        }
    }
}
