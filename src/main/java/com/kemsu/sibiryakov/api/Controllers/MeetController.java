package com.kemsu.sibiryakov.api.Controllers;

import com.kemsu.sibiryakov.api.DTOs.CreateMeetDTO;
import com.kemsu.sibiryakov.api.Entities.MeetPart.Meet;
import com.kemsu.sibiryakov.api.JwtFilter.JwtFilter;
import com.kemsu.sibiryakov.api.Repositories.IMeetRepository;
import com.kemsu.sibiryakov.api.Services.MeetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meet")
public class MeetController {
    private final MeetService meetService;

    @Autowired
    public MeetController(MeetService meetService) {
        this.meetService = meetService;
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Meet> getAllMeet() {
        return meetService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Meet> getMeetById(@PathVariable Long id) {
        Meet meet = meetService.getById(id);
        return meet != null
                ? new ResponseEntity<>(meet, HttpStatusCode.valueOf(200))
                : new ResponseEntity<>(HttpStatusCode.valueOf(404));
    }

    @PostMapping("/create")
    public ResponseEntity<Meet> createMeet(@RequestBody CreateMeetDTO createMeetDTO,
                                           @CookieValue("jwt") String jwt) {
        Long organizerId =
                Long.parseLong(
                        JwtFilter.getBody(jwt)
                                .get("id")
                                .toString()
                );

        Meet meet = meetService.createMeet(
                organizerId,
                meetService.prepareToCreate(createMeetDTO)
        );
        System.out.println(meet);
        return meet != null
                ? new ResponseEntity<>(meet, HttpStatusCode.valueOf(201))
                : new ResponseEntity<>(HttpStatusCode.valueOf(400));
    }
}
