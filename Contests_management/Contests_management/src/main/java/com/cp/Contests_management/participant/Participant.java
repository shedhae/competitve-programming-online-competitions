package com.cp.Contests_management.participant;


import com.cp.Contests_management.participant_competition.ParticipantCompetition;

import com.cp.Contests_management.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
/*
    this class will contain the participants
    who might compete in certain competitions
    or to submit solutions outside the scope
    of a competition .

    A participant in a competition is a team
    that has a unique name, and it's size between
    zero and three.

    **** By default, when creating a user entity
    a participant entity will be created with
    the same name of the user. ******

*/
public class Participant {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(
            nullable = false,
            unique = true
    )
    private String name;

    @ManyToMany
    @JoinTable(
        name = "user_participants",
            joinColumns = {
                @JoinColumn(name = "participant_id")
            },
            inverseJoinColumns = {
                @JoinColumn(name = "user_id")
            }

    )
    @Size(
            max = 3,
            min = 1,
            message = "A team can have at most 3 members and at least 1 member"
    )
    private List<User> users;


    @OneToMany(mappedBy = "participant")
    private List<ParticipantCompetition> participantsCompetitions;

    public int getUserCount() {
        return users.size();
    }

}
