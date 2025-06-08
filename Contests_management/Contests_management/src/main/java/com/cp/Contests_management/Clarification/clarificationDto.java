package com.cp.Contests_management.Clarification;


import jakarta.validation.constraints.NotBlank;

public record clarificationDto(
    
    @NotBlank(message = "Content cannot be empty") 
    String content
) {}

