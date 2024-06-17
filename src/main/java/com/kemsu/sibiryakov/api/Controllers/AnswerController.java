package com.kemsu.sibiryakov.api.Controllers;

import com.kemsu.sibiryakov.api.DTOs.BanDTO;
import com.kemsu.sibiryakov.api.DTOs.CreateAnswerDTO;
import com.kemsu.sibiryakov.api.Entities.Emuns.ERole;
import com.kemsu.sibiryakov.api.Entities.MeetPart.Answer;
import com.kemsu.sibiryakov.api.JwtFilter.JwtFilter;
import com.kemsu.sibiryakov.api.Services.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.kemsu.sibiryakov.api.Services.RightsService.checkRight;

@RestController
@RequestMapping("/api/answer")
public class AnswerController {
    private final AnswerService answerService;

    @Autowired
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Answer> getById(@PathVariable Long id,
                                          @CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.USER, ERole.MODERATOR, ERole.ADMINISTRATOR,
                ERole.ORGANIZER, ERole.ADMINISTRATION)) {
            Answer answer = answerService.getById(id);

            return answer != null
                    ? new ResponseEntity<>(answer, HttpStatusCode.valueOf(200))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(404));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @PostMapping("/ban")
    public ResponseEntity<Answer> banComment(@RequestBody BanDTO banDTO,
                                             @CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.MODERATOR, ERole.ADMINISTRATOR)) {
            Long moderId = Long.parseLong(
                    JwtFilter.getBody(jwt)
                            .get("id")
                            .toString()
            );

            Answer answer = answerService.banQuestion(banDTO, moderId);

            return answer != null
                    ? new ResponseEntity<>(answer, HttpStatusCode.valueOf(200))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(400));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Answer> createAnswer(@RequestBody CreateAnswerDTO createAnswerDTO,
                                               @CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.USER)) {
            Long userId = Long.parseLong(
                    JwtFilter.getBody(jwt)
                            .get("id")
                            .toString()
            );

            Answer answer = answerService.createAnswer(createAnswerDTO, userId);

            return answer != null
                    ? new ResponseEntity<>(answer, HttpStatusCode.valueOf(201))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(400));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }
}
