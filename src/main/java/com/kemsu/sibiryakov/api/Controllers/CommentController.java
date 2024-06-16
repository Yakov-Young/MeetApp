package com.kemsu.sibiryakov.api.Controllers;

import com.kemsu.sibiryakov.api.DTOs.CreateCommentDTO;
import com.kemsu.sibiryakov.api.DTOs.BanDTO;
import com.kemsu.sibiryakov.api.Entities.Emuns.EContentStatus;
import com.kemsu.sibiryakov.api.Entities.Emuns.ERole;
import com.kemsu.sibiryakov.api.Entities.MeetPart.Comment;
import com.kemsu.sibiryakov.api.JwtFilter.JwtFilter;
import com.kemsu.sibiryakov.api.Services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.kemsu.sibiryakov.api.Services.RightsService.checkRight;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getById(@PathVariable Long id,
                                           @CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.USER, ERole.MODERATOR, ERole.ADMINISTRATOR,
                ERole.ORGANIZER, ERole.ADMINISTRATION)) {
            Comment comment = commentService.getById(id);

            return comment != null
                    ? new ResponseEntity<>(comment, HttpStatusCode.valueOf(200))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(404));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @GetMapping("/getByMeet/active/{id}")
    public ResponseEntity<List<Comment>> getActiveByMeet(@PathVariable("id") Long meetId,
                                                         @CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.USER, ERole.MODERATOR, ERole.ADMINISTRATOR)) {
            List<Comment> comments = commentService.getByMeet(meetId);
            List<Comment> activeComment = new ArrayList<>();

            for (Comment c : comments) {
                if (c.getStatus().getStatus().equals(EContentStatus.ACTIVE)) {
                    activeComment.add(c);
                }
            }

            return !activeComment.isEmpty()
                    ? new ResponseEntity<>(activeComment, HttpStatusCode.valueOf(200))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(404));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @GetMapping("/getByMeet/all/{id}")
    public ResponseEntity<List<Comment>> getAllByMeet(@PathVariable("id") Long meetId,
                                                      @CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.MODERATOR, ERole.ADMINISTRATOR)) {
            List<Comment> comments = commentService.getByMeet(meetId);

            return !comments.isEmpty()
                    ? new ResponseEntity<>(comments, HttpStatusCode.valueOf(200))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(404));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @PostMapping("/ban")
    public ResponseEntity<Comment> banComment(@RequestBody BanDTO banDTO,
                                              @CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.MODERATOR, ERole.ADMINISTRATOR)) {
            Long moderId = Long.parseLong(
                    JwtFilter.getBody(jwt)
                            .get("id")
                            .toString()
                );

            Comment comment = commentService.banComment(banDTO, moderId);

            return comment != null
                    ? new ResponseEntity<>(comment, HttpStatusCode.valueOf(200))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(400));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Comment> createComment(@RequestBody CreateCommentDTO createCommentDTO,
                                                 @CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.USER)) {
            Long userId = Long.parseLong(
                    JwtFilter.getBody(jwt)
                            .get("id")
                            .toString()
            );

            Comment comment = commentService.createComment(createCommentDTO, userId);

            return comment != null
                    ? new ResponseEntity<>(comment, HttpStatusCode.valueOf(201))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(400));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

}
