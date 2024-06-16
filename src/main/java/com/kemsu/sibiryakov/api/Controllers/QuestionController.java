package com.kemsu.sibiryakov.api.Controllers;

import com.kemsu.sibiryakov.api.DTOs.CreateQuestionDTO;
import com.kemsu.sibiryakov.api.Entities.Emuns.EContentStatus;
import com.kemsu.sibiryakov.api.Entities.MeetPart.Answer;
import com.kemsu.sibiryakov.api.Entities.MeetPart.Comment;
import com.kemsu.sibiryakov.api.Entities.MeetPart.Question;
import com.kemsu.sibiryakov.api.JwtFilter.JwtFilter;
import com.kemsu.sibiryakov.api.Services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/getByMeet/active/{id}")
    public ResponseEntity<List<Question>> getActiveByMeet(@PathVariable("id") Long meetId,
                                                    @CookieValue("jwt") String jwt) {
        List<Question> questions = questionService.getByMeet(meetId);
        List<Question> activeQuestion = new ArrayList<>();

        for (Question q : questions) {
            if (q.getStatus().getStatus().equals(EContentStatus.ACTIVE)) {
                activeQuestion.add(q);
            }
        }

        return !activeQuestion.isEmpty()
                ? new ResponseEntity<>(activeQuestion, HttpStatusCode.valueOf(200))
                : new ResponseEntity<>(HttpStatusCode.valueOf(404));
    }

    @GetMapping("/getByMeet/all/{id}")
    public ResponseEntity<List<Question>> getAllByMeet(@PathVariable("id") Long meetId,
                                                       @CookieValue("jwt") String jwt) {
        List<Question> questions = questionService.getByMeet(meetId);

        return !questions.isEmpty()
                ? new ResponseEntity<>(questions, HttpStatusCode.valueOf(200))
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
