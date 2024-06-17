package com.kemsu.sibiryakov.api.Services;

import com.kemsu.sibiryakov.api.DTOs.BanDTO;
import com.kemsu.sibiryakov.api.DTOs.CreateCommentDTO;
import com.kemsu.sibiryakov.api.Entities.MeetPart.Comment;
import com.kemsu.sibiryakov.api.Entities.MeetPart.ContentStatus;
import com.kemsu.sibiryakov.api.Entities.MeetPart.Meet;
import com.kemsu.sibiryakov.api.Entities.UserPart.User;
import com.kemsu.sibiryakov.api.Repositories.ICommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {
    private final ICommentRepository commentRepository;
    private final MeetService meetService;
    private final UserService userService;
    private final ContentStatusService contentStatusService;

    @Autowired
    public CommentService(ICommentRepository commentRepository, MeetService meetService,
                          UserService userService, ContentStatusService contentStatusService) {
        this.commentRepository = commentRepository;
        this.meetService = meetService;
        this.userService = userService;
        this.contentStatusService = contentStatusService;
    }

    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    public Comment getById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    public Comment createComment(CreateCommentDTO createCommentDTO, Long userId) {
        Comment comment = new Comment(createCommentDTO.getContent(), createCommentDTO.getGrade());

        comment.setStatus(
                contentStatusService.createStatus(
                        new ContentStatus().setActive())
        );

        comment.setUser(
                userService.getById(userId)
        );

        comment.setMeet(
                meetService.getById(createCommentDTO.getMeetId())
        );

        comment.setCreatedAt(
                LocalDateTime.now()
        );

        return commentRepository.save(comment);
    }

    public List<Comment> getByMeet(Long meetId) {
        Meet meet = meetService.getById(meetId);

        return commentRepository.findByMeet(meet);
    }

    public List<Comment> getByUser(Long userID) {
        User user = userService.getById(userID);

        return commentRepository.findByUser(user);
    }

    public Comment banComment(BanDTO banDTO, Long moderId) {
        Comment comment = this.getById(banDTO.getId());

        ContentStatus status = comment.getStatus().setBanned();

        User moder = userService.getById(moderId);

        status.setUser(moder);
        status.setNote(banDTO.getContent());
        status.setCreatedAt(LocalDateTime.now());

        userService.setWaringStatus(comment.getUser(), moder);

        return commentRepository.save(comment);
    }
}
