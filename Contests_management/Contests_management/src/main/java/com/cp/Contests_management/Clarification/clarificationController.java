package com.cp.Contests_management.Clarification;

import java.util.List;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("clarifications")
public class clarificationController {

    private final clarificationService clarificationService;

    public clarificationController(clarificationService clarificationService) {
        this.clarificationService = clarificationService;
    }

    @PostMapping("/{participantId}/{problemId}")
    public clarificationResponseDto createClarification(
            @PathVariable Integer participantId,
            @PathVariable Integer problemId,
            @RequestBody clarificationDto clarificationDto) {

     return  clarificationService.createClarification(clarificationDto, problemId, participantId);
        
    }

   

 @GetMapping("/{problemId}")
    public List<clarificationResponseDto> getByProblemId(@PathVariable Integer problemId) {
        return clarificationService.getClarificationsByProblem(problemId);
        
    }


   @GetMapping("/{id}")
    public clarificationResponseDto getById(@PathVariable Integer id) {
        return clarificationService.getClarificationById(id);
}



 @PutMapping("{clarification_id}")
    public clarificationResponseDto updateClarificationById(
            @PathVariable("clarification_id") Integer clarificationId,
            @Valid @RequestBody clarificationDto clarificationDto
    )
    {
        return clarificationService.updateClarification(clarificationId,clarificationDto);

    }


@DeleteMapping("{clarification_id}")
    public void deleteClarification(@PathVariable Integer clarificationId) {
       clarificationService.deleteClarification(clarificationId);
    }
  
    
}
