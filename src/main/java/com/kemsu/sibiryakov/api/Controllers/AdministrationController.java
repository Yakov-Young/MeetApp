package com.kemsu.sibiryakov.api.Controllers;


import com.kemsu.sibiryakov.api.Entities.UserPart.Administration;
import com.kemsu.sibiryakov.api.Services.AdministrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/administration")
public class AdministrationController {
    public final AdministrationService administrationService;

    @Autowired
    public AdministrationController(AdministrationService administrationService) {
        this.administrationService = administrationService;
    }

    @GetMapping("/{id}")
    public Optional<Administration> getById(@PathVariable Long id) {
        return administrationService.getById(id);
    }

}
