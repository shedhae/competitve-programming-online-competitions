package com.cp.Contests_management.submission;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/submissions")
public class submissionController {

    private final submissionService submissionService;

    public submissionController(submissionService submissionService) {
        this.submissionService = submissionService;
    }

    @PostMapping("/create/{Problem_id}/{participant_id}")
    public ResponseEntity<submissionResponseDto> createSubmission(@RequestBody submissionDto submissionDto,
      @PathVariable("participant_id") Integer participant_id,
      @PathVariable("Problem_id") Integer Problem_id)
    
    {
        submissionResponseDto responseDto = submissionService.createSubmission(submissionDto,participant_id,Problem_id);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<submissionResponseDto> getSubmissionById(@PathVariable Integer id) {
        submissionResponseDto responseDto = submissionService.getSubmissionById(id);
        return ResponseEntity.ok(responseDto);
    }
}
