package com.kemsu.sibiryakov.api.Controllers;

import com.kemsu.sibiryakov.api.Entities.UserPart.Organizer;
import com.kemsu.sibiryakov.api.Services.OrganizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/organizer")
public class OrganizerController {
    private final OrganizerService organizerService;
    @Autowired
    public OrganizerController(OrganizerService organizerService) {
        this.organizerService = organizerService;
    }

    @GetMapping("/{id}")
    public Organizer getById(@PathVariable Long id) {
        return organizerService.getById(id);
    }
}
