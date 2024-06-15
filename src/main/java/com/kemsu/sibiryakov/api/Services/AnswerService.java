package com.kemsu.sibiryakov.api.Services;

import com.kemsu.sibiryakov.api.DTOs.CreateAnswerDTO;
import com.kemsu.sibiryakov.api.DTOs.CreateQuestionDTO;
import com.kemsu.sibiryakov.api.Entities.MeetPart.Answer;
import com.kemsu.sibiryakov.api.Entities.MeetPart.ContentStatus;
import com.kemsu.sibiryakov.api.Entities.MeetPart.Question;
import com.kemsu.sibiryakov.api.Repositories.IAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Service
public class AnswerService {
    private final IAnswerRepository answerRepository;
    private final QuestionService questionService;
    private final UserService userService;
    private final ContentStatusService contentStatusService;

    @Autowired
    public AnswerService(IAnswerRepository answerRepository, QuestionService questionService,
                         UserService userService, ContentStatusService contentStatusService) {
        this.answerRepository = answerRepository;
        this.questionService = questionService;
        this.userService = userService;
        this.contentStatusService = contentStatusService;
    }

    public Answer getById(Long id) {
        return answerRepository.findById(id).orElse(null);
    }

    public Answer createQuestion(CreateAnswerDTO createAnswerDTO, Long userId) {
        Answer answer = new Answer(createAnswerDTO.getContent());

        answer.setStatus(
                contentStatusService.createStatus(
                        new ContentStatus().setActive())
        );

        answer.setUser(
                userService.getById(userId)
        );

        answer.setQuestion(
                questionService.getById(createAnswerDTO.getQuestionId())
        );

        answer.setCreatedAt(
                LocalDateTime.now()
        );

        return answerRepository.save(answer);
    }
}
