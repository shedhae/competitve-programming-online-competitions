package com.cp.Contests_management.user;

import com.cp.Contests_management.competition.Competition;
import com.cp.Contests_management.participant.Participant;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name ="app_user")

public class User {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(
            nullable = false,
            unique = true
    )
    private String name;
    @Column(
            nullable = false,
            unique = true
    )
    private String email;
    @Column(
            nullable = false
    )
    private String password;
    private int rating;

    //USER CAN have different participations
    @ManyToMany(
        mappedBy = "users"
    )
    private List<Participant> participations;

    @OneToMany(
            mappedBy = "user"
    )
    @JsonBackReference
    private List<Competition> competitions;
}
