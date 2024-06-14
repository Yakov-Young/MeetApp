package com.kemsu.sibiryakov.api.Controllers;

import com.kemsu.sibiryakov.api.DTOs.UpdateDTO.OrganizerUpdateDTO;
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
    public List<Organizer> getAll() {
        return organizerService.getAllOrganizer();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Organizer> getById(@PathVariable Long id) {
        Organizer organizer = organizerService.getById(id);

        return organizer != null
                ? new ResponseEntity<>(organizer, HttpStatusCode.valueOf(200))
                : new ResponseEntity<>(HttpStatusCode.valueOf(404));
    }

    @PostMapping("/update")
    public ResponseEntity<Organizer> updateOrganizerProfile(@RequestBody OrganizerUpdateDTO organizerUpdateDTO,
                                                            @CookieValue("jwt") String jwt) {
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
