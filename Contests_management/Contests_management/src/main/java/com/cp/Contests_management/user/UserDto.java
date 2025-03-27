package com.cp.Contests_management.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


public record UserDto(
        @NotEmpty(message = "Name cannot be empty. Please provide a valid name.")
        String name,

        @Email(message = "Please enter a valid email address (e.g., user@example.com).")
        @NotEmpty(message = "Email is required. Please provide an email address.")
        String email,

        @NotEmpty(message = "Password is required. Please choose a strong password.")
        @Size(min = 6, message = "Password must be at least 6 characters long.")
        String password
) {
}
