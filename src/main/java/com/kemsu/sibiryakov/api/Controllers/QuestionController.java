package com.kemsu.sibiryakov.api.Controllers;

import com.kemsu.sibiryakov.api.DTOs.CreateQuestionDTO;
import com.kemsu.sibiryakov.api.Entities.MeetPart.Question;
import com.kemsu.sibiryakov.api.JwtFilter.JwtFilter;
import com.kemsu.sibiryakov.api.Services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/question")
public class QuestionController {
    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Question> getById(@PathVariable Long id) {
        Question question = questionService.getById(id);

        return question != null
                ? new ResponseEntity<>(question, HttpStatusCode.valueOf(200))
                : new ResponseEntity<>(HttpStatusCode.valueOf(404));
    }

    @PostMapping("/create")
    public ResponseEntity<Question> createQuestion(@RequestBody CreateQuestionDTO createQuestionDTO,
                                                   @CookieValue("jwt") String jwt) {
        Long userId = Long.parseLong(
                JwtFilter.getBody(jwt)
                        .get("id")
                        .toString()
        );

        Question question = questionService.createQuestion(createQuestionDTO, userId);

        return question != null
                ? new ResponseEntity<>(question, HttpStatusCode.valueOf(201))
                : new ResponseEntity<>(HttpStatusCode.valueOf(400));
    }
}