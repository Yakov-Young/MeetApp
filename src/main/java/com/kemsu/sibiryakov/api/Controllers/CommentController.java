package com.kemsu.sibiryakov.api.Controllers;

import com.kemsu.sibiryakov.api.DTOs.CreateCommentDTO;
import com.kemsu.sibiryakov.api.DTOs.MeetDTO.BanDTO;
import com.kemsu.sibiryakov.api.Entities.Emuns.EContentStatus;
import com.kemsu.sibiryakov.api.Entities.MeetPart.Comment;
import com.kemsu.sibiryakov.api.JwtFilter.JwtFilter;
import com.kemsu.sibiryakov.api.Services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getById(@PathVariable Long id) {
        Comment comment = commentService.getById(id);

        return comment != null
                ? new ResponseEntity<>(comment, HttpStatusCode.valueOf(200))
                : new ResponseEntity<>(HttpStatusCode.valueOf(404));
    }

    @GetMapping("/getByMeet/{id}")
    public ResponseEntity<List<Comment>> getByMeet(@PathVariable("id") Long meetId,
                                                   @CookieValue("jwt") String jwt) {
        List<Comment> comments = commentService.getByMeet(meetId);
        List<Comment> activeComment = new ArrayList<>();

        for (Comment c : comments) {
            if (c.getStatus().equals(EContentStatus.ACTIVE.getState())) {
                activeComment.add(c);
            }
        }

        return !activeComment.isEmpty()
                ? new ResponseEntity<>(comments, HttpStatusCode.valueOf(200))
                : new ResponseEntity<>(HttpStatusCode.valueOf(404));
    }

    @PostMapping("/ban")
    public ResponseEntity<Comment> banComment(@RequestBody BanDTO banDTO,
                                              @CookieValue("jwt") String jwt) {
        Long moderId = Long.parseLong(
                JwtFilter.getBody(jwt)
                        .get("id")
                        .toString()
        );

        Comment comment = commentService.banComment(banDTO, moderId);

        return new ResponseEntity<>(comment, HttpStatusCode.valueOf(200));
    }

    @PostMapping("/create")
    public ResponseEntity<Comment> createComment(@RequestBody CreateCommentDTO createCommentDTO,
                                                 @CookieValue("jwt") String jwt) {
        Long userId = Long.parseLong(
                JwtFilter.getBody(jwt)
                        .get("id")
                        .toString()
        );

        Comment comment = commentService.createComment(createCommentDTO, userId);

        return comment != null
                ? new ResponseEntity<>(comment, HttpStatusCode.valueOf(201))
                : new ResponseEntity<>(HttpStatusCode.valueOf(400));
    }

}
