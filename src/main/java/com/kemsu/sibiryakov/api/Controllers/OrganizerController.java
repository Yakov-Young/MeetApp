package com.kemsu.sibiryakov.api.Controllers;

import com.kemsu.sibiryakov.api.Entities.UserPart.Organizer;
import com.kemsu.sibiryakov.api.Entities.UserPart.User;
import com.kemsu.sibiryakov.api.Services.OrganizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/organizer")
public class OrganizerController {
    private final OrganizerService organizerService;
    @Autowired
    public OrganizerController(OrganizerService organizerService) {
        this.organizerService = organizerService;
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
}
