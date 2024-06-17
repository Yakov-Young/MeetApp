package com.kemsu.sibiryakov.api.Controllers;

import com.kemsu.sibiryakov.api.DTOs.BanDTO;
import com.kemsu.sibiryakov.api.DTOs.MeetDTO.AgreeMeetDTO;
import com.kemsu.sibiryakov.api.DTOs.MeetDTO.CreateMeetDTO;
import com.kemsu.sibiryakov.api.Entities.Emuns.ERole;
import com.kemsu.sibiryakov.api.Entities.MeetPart.Meet;
import com.kemsu.sibiryakov.api.Entities.MeetUser;
import com.kemsu.sibiryakov.api.Entities.UserPart.Searches;
import com.kemsu.sibiryakov.api.JwtFilter.JwtFilter;
import com.kemsu.sibiryakov.api.Repositories.ISearchRepository;
import com.kemsu.sibiryakov.api.Services.MeetService;
import com.kemsu.sibiryakov.api.Services.MeetUserService;
import com.kemsu.sibiryakov.api.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.kemsu.sibiryakov.api.Services.RightsService.checkRight;

@RestController
@RequestMapping("/api/meet")
public class MeetController {
    private final MeetService meetService;
    private final MeetUserService meetUserService;
    private final ISearchRepository searchRepository;
    private final UserService userService;

    @Autowired
    public MeetController(MeetService meetService, MeetUserService meetUserService,
                          ISearchRepository searchRepository, UserService userService) {
        this.meetService = meetService;
        this.meetUserService = meetUserService;
        this.searchRepository = searchRepository;
        this.userService = userService;
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Meet>> getAllMeet(@CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.ADMINISTRATOR, ERole.ADMINISTRATION)) {
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
        if (checkRight(jwt, ERole.MODERATOR, ERole.ADMINISTRATOR,
                ERole.ORGANIZER, ERole.ADMINISTRATION)) {
            Meet meet = meetService.getById(id);
            return meet != null
                    ? new ResponseEntity<>(meet, HttpStatusCode.valueOf(200))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(404));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Set<Meet>> getMeetByCategory(@PathVariable Long category,
                                                       @CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.USER, ERole.MODERATOR, ERole.ADMINISTRATOR,
                ERole.ORGANIZER, ERole.ADMINISTRATION)) {

            Set<Meet> meets = meetService.getByCategory(category);

            return !meets.isEmpty()
                    ? new ResponseEntity<>(meets, HttpStatusCode.valueOf(200))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(404));

        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Meet>> getMeetByTitle(@RequestParam String title,
                                                     @CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.USER, ERole.MODERATOR, ERole.ADMINISTRATOR,
                ERole.ORGANIZER, ERole.ADMINISTRATION)) {

            List<Meet> meets = meetService.getAll();
            List<Meet> result = new ArrayList<>();

            Pattern pattern = Pattern.compile(".*" + title + ".*");
            Matcher matcher;

            for (Meet m : meets) {
                matcher = pattern.matcher(m.getTitle());
                if (matcher.find()) {
                    result.add(m);
                }
            }

            if (ERole.valueOf(JwtFilter.getBody(jwt)
                    .get("role")
                    .toString()
                    .toUpperCase()).equals(ERole.USER)) {
                Searches search = new Searches(
                        title,
                        userService.getById(
                                Long.parseLong(
                                        JwtFilter.getBody(jwt)
                                                .get("id")
                                                .toString()
                                )),
                        LocalDateTime.now());

                searchRepository.save(search);

            }

            return !result.isEmpty()
                    ? new ResponseEntity<>(result, HttpStatusCode.valueOf(200))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(404));

        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @GetMapping(value = "/searchWithCategory")
    public ResponseEntity<List<Meet>> getMeetByTitleAndCategory(@RequestParam String title,
                                                                @RequestParam Long category,
                                                                @CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.USER, ERole.MODERATOR, ERole.ADMINISTRATOR,
                ERole.ORGANIZER, ERole.ADMINISTRATION)) {

            Set<Meet> meets = meetService.getByCategory(category);
            ;
            List<Meet> result = new ArrayList<>();

            Pattern pattern = Pattern.compile(".*" + title + ".*");
            Matcher matcher;

            for (Meet m : meets) {
                matcher = pattern.matcher(m.getTitle());
                if (matcher.find()) {
                    result.add(m);
                }
            }


            if (ERole.valueOf(JwtFilter.getBody(jwt)
                    .get("role")
                    .toString()
                    .toUpperCase()).equals(ERole.USER)) {
                Searches search = new Searches(
                        title,
                        userService.getById(
                                Long.parseLong(
                                        JwtFilter.getBody(jwt)
                                                .get("id")
                                                .toString()
                                )),
                        LocalDateTime.now());

                searchRepository.save(search);

            }

            return !result.isEmpty()
                    ? new ResponseEntity<>(result, HttpStatusCode.valueOf(200))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(404));

        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @GetMapping("/user/my")
    public ResponseEntity<List<Meet>> getMyMeet(@CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.USER)) {
            Long userId = Long.parseLong(
                    JwtFilter.getBody(jwt)
                            .get("id")
                            .toString()
            );

            List<MeetUser> meetUser = meetUserService.myMeet(userId);

            List<Meet> meets = new ArrayList<>();

            for (MeetUser m : meetUser) {
                meets.add(m.getMeet());
            }

            return !meets.isEmpty()
                    ? new ResponseEntity<>(meets, HttpStatusCode.valueOf(201))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(400));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @GetMapping("/user/my/last")
    public ResponseEntity<List<Meet>> getMyLastMeet(@CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.USER)) {
            Long userId = Long.parseLong(
                    JwtFilter.getBody(jwt)
                            .get("id")
                            .toString()
            );

            List<MeetUser> meetUser = meetUserService.myLastMeet(userId);

            List<Meet> meets = new ArrayList<>();

            for (MeetUser m : meetUser) {
                meets.add(m.getMeet());
            }

            return !meets.isEmpty()
                    ? new ResponseEntity<>(meets, HttpStatusCode.valueOf(201))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(400));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @GetMapping("/user/my/future")
    public ResponseEntity<List<Meet>> getMyFutureMeet(@CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.USER)) {
            Long userId = Long.parseLong(
                    JwtFilter.getBody(jwt)
                            .get("id")
                            .toString()
            );

            List<MeetUser> meetUser = meetUserService.myFutureMeet(userId);

            List<Meet> meets = new ArrayList<>();

            for (MeetUser m : meetUser) {
                meets.add(m.getMeet());
            }

            return !meets.isEmpty()
                    ? new ResponseEntity<>(meets, HttpStatusCode.valueOf(201))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(400));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @PostMapping("/view/{id}")
    public ResponseEntity<Meet> viewMeet(@PathVariable("id") Long meetId,
                                         @CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.USER)) {
            Long userId = Long.parseLong(
                    JwtFilter.getBody(jwt)
                            .get("id")
                            .toString()
            );

            MeetUser meetUser = meetUserService.viewMeet(meetId, userId);

            return meetUser != null
                    ? new ResponseEntity<>(meetUser.getMeet(), HttpStatusCode.valueOf(201))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(400));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @DeleteMapping("/noVisit/{id}")
    public ResponseEntity<?> noVisitMeet(@PathVariable("id") Long meetId,
                                         @CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.USER)) {
            Long userId = Long.parseLong(
                    JwtFilter.getBody(jwt)
                            .get("id")
                            .toString()
            );

            boolean isAccess = meetUserService.noVisitMeet(meetId, userId);

            return isAccess
                    ? new ResponseEntity<>(HttpStatusCode.valueOf(201))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(400));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @PostMapping("/visit/{id}")
    public ResponseEntity<Meet> visitMeet(@PathVariable("id") Long meetId,
                                          @CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.USER)) {
            Long userId = Long.parseLong(
                    JwtFilter.getBody(jwt)
                            .get("id")
                            .toString()
            );

            MeetUser meetUser = meetUserService.visitMeet(meetId, userId);

            return meetUser != null
                    ? new ResponseEntity<>(meetUser.getMeet(), HttpStatusCode.valueOf(201))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(400));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @GetMapping("/organizer/my")
    public ResponseEntity<List<Meet>> getMyOrgMeet(@CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.ORGANIZER)) {
            Long organizerId = Long.parseLong(
                    JwtFilter.getBody(jwt)
                            .get("id")
                            .toString()
            );

            List<Meet> meets = meetService.myMeet(organizerId);

            return !meets.isEmpty()
                    ? new ResponseEntity<>(meets, HttpStatusCode.valueOf(201))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(400));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @GetMapping("/organizer/my/future")
    public ResponseEntity<List<Meet>> getMyFutureOrgMeet(@CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.ORGANIZER)) {
            Long organizerId = Long.parseLong(
                    JwtFilter.getBody(jwt)
                            .get("id")
                            .toString()
            );

            List<Meet> futureMeets = meetService.myFutureMeet(organizerId);

            return !futureMeets.isEmpty()
                    ? new ResponseEntity<>(futureMeets, HttpStatusCode.valueOf(201))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(400));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @GetMapping("/organizer/my/last")
    public ResponseEntity<List<Meet>> getMyLastOrgMeet(@CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.ORGANIZER)) {
            Long organizerId = Long.parseLong(
                    JwtFilter.getBody(jwt)
                            .get("id")
                            .toString()
            );

            List<Meet> lastMeets = meetService.myLastMeet(organizerId);

            return !lastMeets.isEmpty()
                    ? new ResponseEntity<>(lastMeets, HttpStatusCode.valueOf(201))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(400));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @GetMapping("/futureMeet")
    public ResponseEntity<List<Meet>> getFutureMeetByPlace(@CookieValue(value = "jwt", required = false) String jwt) {
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
    public ResponseEntity<List<Meet>> getLastMeetByPlace(@CookieValue(value = "jwt", required = false) String jwt) {
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

    @PostMapping("/canceled")
    public ResponseEntity<Meet> canceledMeet(@RequestBody BanDTO banDTO,
                                             @CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.ORGANIZER)) {
            Long administrationId = Long.parseLong(
                    JwtFilter.getBody(jwt)
                            .get("id")
                            .toString()
            );

            Meet meet = meetService.canceledMeet(banDTO);

            return new ResponseEntity<>(meet, HttpStatusCode.valueOf(200));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }


}
