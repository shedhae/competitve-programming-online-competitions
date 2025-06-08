package com.cp.Contests_management.submission;

import java.time.LocalDateTime;

public record submissionResponseDto(   
    LocalDateTime time,
    String language,
    String judgment,
    String code,
    String problem
    ) {

}
