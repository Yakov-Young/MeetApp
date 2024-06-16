package com.kemsu.sibiryakov.api.Controllers;

import com.kemsu.sibiryakov.api.DTOs.UpdateDTO.OrganizerUpdateDTO;
import com.kemsu.sibiryakov.api.Entities.Emuns.ERole;
import com.kemsu.sibiryakov.api.Entities.Emuns.Gender;
import com.kemsu.sibiryakov.api.Entities.UserPart.Organizer;
import com.kemsu.sibiryakov.api.Entities.UserPart.OrganizerPhoneNumber;
import com.kemsu.sibiryakov.api.Entities.UserPart.User;
import com.kemsu.sibiryakov.api.JwtFilter.JwtFilter;
import com.kemsu.sibiryakov.api.Services.OrganizerPhoneNumberService;
import com.kemsu.sibiryakov.api.Services.OrganizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.kemsu.sibiryakov.api.Services.RightsService.checkRight;

@RestController
@RequestMapping("api/organizer")
public class OrganizerController {
    private final OrganizerService organizerService;
    private final OrganizerPhoneNumberService organizerPhoneNumberService;
    @Autowired
    public OrganizerController(OrganizerService organizerService, OrganizerPhoneNumberService organizerPhoneNumberService) {
        this.organizerService = organizerService;
        this.organizerPhoneNumberService = organizerPhoneNumberService;
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Organizer>> getAll(@CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.MODERATOR, ERole.ADMINISTRATOR)) {
            List<Organizer> organizers = organizerService.getAllOrganizer();

            return !organizers.isEmpty()
                    ? new ResponseEntity<>(organizers, HttpStatusCode.valueOf(200))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(404));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Organizer> getById(@PathVariable Long id,
                                             @CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.USER, ERole.MODERATOR, ERole.ADMINISTRATOR, ERole.ORGANIZER, ERole.ADMINISTRATION)) {
            Organizer organizer = organizerService.getById(id);

            return organizer != null
                    ? new ResponseEntity<>(organizer, HttpStatusCode.valueOf(200))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(404));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @GetMapping("/allBanned")
    public ResponseEntity<List<Organizer>> getBannedOrganizer(@CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.MODERATOR, ERole.ADMINISTRATOR)) {
            List<Organizer> bannedOrganizers = organizerService.getBannedOrganizers();

            return !bannedOrganizers.isEmpty()
                    ? new ResponseEntity<>(bannedOrganizers, HttpStatusCode.valueOf(200))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(404));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @GetMapping("/allActive")
    public ResponseEntity<List<Organizer>> getActiveOrganizer(@CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.MODERATOR, ERole.ADMINISTRATOR)) {
            List<Organizer> activeOrganizers = organizerService.getActiveOrganizers();

            return !activeOrganizers.isEmpty()
                    ? new ResponseEntity<>(activeOrganizers, HttpStatusCode.valueOf(200))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(404));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @GetMapping("/allWarring")
    public ResponseEntity<List<Organizer>> getWarringOrganizer(@CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.MODERATOR, ERole.ADMINISTRATOR)) {
            List<Organizer> warringOrganizers = organizerService.getWarringOrganizers();

            return !warringOrganizers.isEmpty()
                    ? new ResponseEntity<>(warringOrganizers, HttpStatusCode.valueOf(200))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(404));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @GetMapping("/allDeleted")
    public ResponseEntity<List<Organizer>> getDeletedOrganizer(@CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.MODERATOR, ERole.ADMINISTRATOR)) {
            List<Organizer> deletedOrganizers = organizerService.getDeletedOrganizers();

            return !deletedOrganizers.isEmpty()
                    ? new ResponseEntity<>(deletedOrganizers, HttpStatusCode.valueOf(200))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(404));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @PostMapping("/selfDelete")
    public ResponseEntity<Organizer> deleteUser(@CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.ORGANIZER)) {
            Long organizerId = Long.parseLong(
                    JwtFilter.getBody(jwt)
                            .get("id")
                            .toString()
            );

            Organizer organizer = organizerService.deleteOrganizer(organizerId);

            return organizer != null
                    ? new ResponseEntity<>(organizer, HttpStatusCode.valueOf(200))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(400));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @PostMapping("/update")
    public ResponseEntity<Organizer> updateOrganizerProfile(@RequestBody OrganizerUpdateDTO organizerUpdateDTO,
                                                            @CookieValue(value = "jwt", required = false) String jwt) {
        Long organizerId =
                Long.parseLong(
                        JwtFilter.getBody(jwt)
                                .get("id")
                                .toString()
                );

        Organizer organizer = organizerService.updateProfile(organizerId, organizerUpdateDTO);

        return organizer != null
                ? new ResponseEntity<>(organizer,HttpStatusCode.valueOf(200))
                : new ResponseEntity<>(HttpStatusCode.valueOf(406));
    }
}
