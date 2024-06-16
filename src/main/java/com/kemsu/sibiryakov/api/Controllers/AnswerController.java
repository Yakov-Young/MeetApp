package com.kemsu.sibiryakov.api.Controllers;

import com.kemsu.sibiryakov.api.DTOs.BanDTO;
import com.kemsu.sibiryakov.api.DTOs.CreateAnswerDTO;
import com.kemsu.sibiryakov.api.Entities.MeetPart.Answer;
import com.kemsu.sibiryakov.api.Entities.MeetPart.Question;
import com.kemsu.sibiryakov.api.JwtFilter.JwtFilter;
import com.kemsu.sibiryakov.api.Services.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/answer")
public class AnswerController {
    private final AnswerService answerService;

    @Autowired
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Answer> getById(@PathVariable Long id) {
        Answer answer = answerService.getById(id);

        return answer != null
                ? new ResponseEntity<>(answer, HttpStatusCode.valueOf(200))
                : new ResponseEntity<>(HttpStatusCode.valueOf(404));
    }

    @PostMapping("/ban")
    public ResponseEntity<Answer> banComment(@RequestBody BanDTO banDTO,
                                             @CookieValue("jwt") String jwt) {
        Long moderId = Long.parseLong(
                JwtFilter.getBody(jwt)
                        .get("id")
                        .toString()
        );

        Answer answer = answerService.banQuestion(banDTO, moderId);

        return answer != null
                ? new ResponseEntity<>(answer, HttpStatusCode.valueOf(200))
                : new ResponseEntity<>(HttpStatusCode.valueOf(400));
    }

    @PostMapping("/create")
    public ResponseEntity<Answer> createAnswer(@RequestBody CreateAnswerDTO createAnswerDTO,
                                               @CookieValue("jwt") String jwt) {
        Long userId = Long.parseLong(
                JwtFilter.getBody(jwt)
                        .get("id")
                        .toString()
        );

        Answer answer = answerService.createAnswer(createAnswerDTO, userId);

        return answer != null
                ? new ResponseEntity<>(answer, HttpStatusCode.valueOf(201))
                : new ResponseEntity<>(HttpStatusCode.valueOf(400));
    }
}
