package com.cp.Contests_management.user;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByName(String name);
    List<User> findByNameContaining(String name);
    @Transactional
    void deleteByName(String userName);

    boolean existsByName(String userName);

    boolean existsByEmail(@NotEmpty(message = "email must be not empty") @Email(message = "Invalid format") String email);
}
