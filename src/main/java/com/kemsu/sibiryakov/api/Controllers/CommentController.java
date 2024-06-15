package com.kemsu.sibiryakov.api.Controllers.Exception;

import com.kemsu.sibiryakov.api.DTOs.CreateCommentDTO;
import com.kemsu.sibiryakov.api.Entities.MeetPart.Comment;
import com.kemsu.sibiryakov.api.Entities.UserPart.User;
import com.kemsu.sibiryakov.api.JwtFilter.JwtFilter;
import com.kemsu.sibiryakov.api.Services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/create")
    public ResponseEntity<Comment> createComment(@RequestBody CreateCommentDTO createCommentDTO,
                                                 @CookieValue("jwt") String jwt) {
        Long userId = Long.parseLong(
                JwtFilter.getBody(jwt)
                        .get("id")
                        .toString()
        );

        Comment comment = commentService.createComment(createCommentDTO, userId);

        return new ResponseEntity<>(comment, HttpStatusCode.valueOf(201));
    }

}
