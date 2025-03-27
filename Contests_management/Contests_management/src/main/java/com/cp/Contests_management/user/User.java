package com.cp.Contests_management.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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




}
