package com.kemsu.sibiryakov.api.Services;

import com.kemsu.sibiryakov.api.DTOs.BanDTO;
import com.kemsu.sibiryakov.api.DTOs.CreateAnswerDTO;
import com.kemsu.sibiryakov.api.Entities.MeetPart.Answer;
import com.kemsu.sibiryakov.api.Entities.MeetPart.ContentStatus;
import com.kemsu.sibiryakov.api.Entities.MeetPart.Question;
import com.kemsu.sibiryakov.api.Entities.UserPart.User;
import com.kemsu.sibiryakov.api.Repositories.IAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Answer createAnswer(CreateAnswerDTO createAnswerDTO, Long userId) {
        Answer answer = new Answer(createAnswerDTO.getContent());

        answer.setStatus(
                contentStatusService.createStatus(
                        new ContentStatus().setActive())
        );

        answer.setUser(
                userService.getById(userId)
        );

        Question question = questionService.getById(createAnswerDTO.getQuestionId());

        if (question.getAnswer() != null) {
            return null;
        }

        answer.setQuestion(
                question
        );

        answer.setCreatedAt(
                LocalDateTime.now()
        );

        return answerRepository.save(answer);
    }

    public Answer banQuestion(BanDTO banDTO, Long moderId) {
        Answer answer = this.getById(banDTO.getId());

        if (answer == null) {
            return null;
        }

        ContentStatus status = answer.getStatus().setBanned();

        User moder = userService.getById(moderId);

        status.setUser(moder);
        status.setNote(banDTO.getContent());
        status.setCreatedAt(LocalDateTime.now());

        userService.setWaringStatus(answer.getUser(), moder);

        return answerRepository.save(answer);
    }
}
