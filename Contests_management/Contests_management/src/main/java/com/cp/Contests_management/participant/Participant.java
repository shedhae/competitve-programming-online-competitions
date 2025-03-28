package com.cp.Contests_management.participant;


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
    who will compete in certain competitions
    a participant in a competition is a team
    that as a unique name and userCount which
    is the number of teammates as maximum = 3
    the userCount must be equal to users.length
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
    List<User> users;


    public int getUserCount() {
        return users.size();
    }

}
