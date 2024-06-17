package com.kemsu.sibiryakov.api.Controllers;

import com.kemsu.sibiryakov.api.DTOs.MeetDTO.AgreeMeetDTO;
import com.kemsu.sibiryakov.api.DTOs.BanDTO;
import com.kemsu.sibiryakov.api.DTOs.MeetDTO.CreateMeetDTO;
import com.kemsu.sibiryakov.api.Entities.Emuns.ERole;
import com.kemsu.sibiryakov.api.Entities.MeetPart.Meet;
import com.kemsu.sibiryakov.api.JwtFilter.JwtFilter;
import com.kemsu.sibiryakov.api.Services.MeetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kemsu.sibiryakov.api.Services.RightsService.checkRight;

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
    public ResponseEntity<List<Meet>> getAllMeet(@CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.ADMINISTRATOR)) {
            List<Meet> meets = meetService.getAll();

            return !meets.isEmpty()
                    ? new ResponseEntity<>(meets, HttpStatusCode.valueOf(200))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(404));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Meet> getMeetById(@PathVariable Long id,
                                            @CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.USER, ERole.MODERATOR, ERole.ADMINISTRATOR,
                ERole.ORGANIZER, ERole.ADMINISTRATION)) {
            Meet meet = meetService.getById(id);
            return meet != null
                    ? new ResponseEntity<>(meet, HttpStatusCode.valueOf(200))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(404));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @GetMapping("/futureMeet")
    public ResponseEntity<List<Meet>> getFutureMeetByPlace(@CookieValue(value = "jwt", required = false) String jwt){
        if (checkRight(jwt, ERole.ADMINISTRATION)) {
            Long administrationId = Long.parseLong(
                    JwtFilter.getBody(jwt)
                            .get("id")
                            .toString()
            );

            List<Meet> meets = meetService.getFutureMeetByPlace(administrationId);

            return !meets.isEmpty()
                    ? new ResponseEntity<>(meets, HttpStatusCode.valueOf(200))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(404));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @GetMapping("/lastMeet")
    public ResponseEntity<List<Meet>> getLastMeetByPlace(@CookieValue(value = "jwt", required = false) String jwt){
        if (checkRight(jwt, ERole.ADMINISTRATION)) {
            Long administrationId = Long.parseLong(
                    JwtFilter.getBody(jwt)
                            .get("id")
                            .toString()
            );

            List<Meet> meets = meetService.getLastMeetByPlace(administrationId);

            return !meets.isEmpty()
                    ? new ResponseEntity<>(meets, HttpStatusCode.valueOf(200))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(404));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Meet> createMeet(@RequestBody CreateMeetDTO createMeetDTO,
                                           @CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.ORGANIZER)) {
            Long organizerId = Long.parseLong(
                    JwtFilter.getBody(jwt)
                            .get("id")
                            .toString()
            );

            Meet meet = meetService.createMeet(
                    organizerId,
                    meetService.prepareToCreate(createMeetDTO)
            );

            return meet != null
                    ? new ResponseEntity<>(meet, HttpStatusCode.valueOf(201))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(400));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @PostMapping("/agree")
    public ResponseEntity<Meet> approvalMeet(@RequestBody AgreeMeetDTO agreeMeetDTO,
                                             @CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.ADMINISTRATION)) {
            Long administrationId = Long.parseLong(
                    JwtFilter.getBody(jwt)
                            .get("id")
                            .toString()
            );

            Meet meet = meetService.approvalMeet(agreeMeetDTO, administrationId);

            return new ResponseEntity<>(meet, HttpStatusCode.valueOf(200));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @PostMapping("/ban")
    public ResponseEntity<Meet> banMeet(@RequestBody BanDTO banDTO,
                                        @CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.ADMINISTRATION)) {
            Long administrationId = Long.parseLong(
                    JwtFilter.getBody(jwt)
                            .get("id")
                            .toString()
            );

            Meet meet = meetService.banMeet(banDTO, administrationId);

            return new ResponseEntity<>(meet, HttpStatusCode.valueOf(200));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }


}
