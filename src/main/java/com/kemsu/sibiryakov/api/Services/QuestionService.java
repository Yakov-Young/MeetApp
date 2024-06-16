package com.kemsu.sibiryakov.api.Services;

import com.kemsu.sibiryakov.api.DTOs.CreateQuestionDTO;
import com.kemsu.sibiryakov.api.Entities.MeetPart.ContentStatus;
import com.kemsu.sibiryakov.api.Entities.MeetPart.Meet;
import com.kemsu.sibiryakov.api.Entities.MeetPart.Question;
import com.kemsu.sibiryakov.api.Repositories.IQuestionRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuestionService {
    private final IQuestionRepositories questionRepositories;
    private final MeetService meetService;
    private final UserService userService;
    private final ContentStatusService contentStatusService;

    @Autowired
    public QuestionService(IQuestionRepositories questionRepositories, MeetService meetService,
                           UserService userService, ContentStatusService contentStatusService) {
        this.questionRepositories = questionRepositories;
        this.meetService = meetService;
        this.userService = userService;
        this.contentStatusService = contentStatusService;
    }


    public Question getById(Long id) {
        return questionRepositories.findById(id).orElse(null);
    }

    public Question createQuestion(CreateQuestionDTO createQuestionDTO, Long userId) {
        Question question = new Question(createQuestionDTO.getContent());

        question.setStatus(
                contentStatusService.createStatus(
                        new ContentStatus().setActive())
        );

        question.setUser(
                userService.getById(userId)
        );

        question.setMeet(
                meetService.getById(createQuestionDTO.getMeetId())
        );

        question.setCreatedAt(
                LocalDateTime.now()
        );

        return questionRepositories.save(question);
    }

    public List<Question> getByMeet(Long meetId) {
        Meet meet = meetService.getById(meetId);

        return questionRepositories.findByMeet(meet);
    }
}
