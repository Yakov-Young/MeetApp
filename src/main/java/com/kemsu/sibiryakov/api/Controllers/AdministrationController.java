package com.kemsu.sibiryakov.api.Controllers;


import com.kemsu.sibiryakov.api.DTOs.UpdateDTO.AdministrationUpdateDTO;
import com.kemsu.sibiryakov.api.DTOs.UpdateDTO.OrganizerUpdateDTO;
import com.kemsu.sibiryakov.api.Entities.UserPart.Administration;
import com.kemsu.sibiryakov.api.JwtFilter.JwtFilter;
import com.kemsu.sibiryakov.api.Services.AdministrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/administration")
public class AdministrationController {
    public final AdministrationService administrationService;

    @Autowired
    public AdministrationController(AdministrationService administrationService) {
        this.administrationService = administrationService;
    }

    @GetMapping("/all")
    public List<Administration> getAll() {
        return administrationService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Administration> getById(@PathVariable Long id) {
        Administration administration = administrationService.getById(id);
        return administration != null
                ? new ResponseEntity<>(administration, HttpStatusCode.valueOf(200))
                : new ResponseEntity<>(HttpStatusCode.valueOf(404));
    }

    @PostMapping("/update")
    public ResponseEntity<Administration> updateAdministrationProfile(@RequestBody AdministrationUpdateDTO administrationUpdateDTO,
                                                                      @CookieValue("jwt") String jwt) {
        Long administrationId =
                Long.parseLong(
                        JwtFilter.getBody(jwt)
                                .get("id")
                                .toString()
                );

        Administration administration = administrationService.updateProfile(administrationId, administrationUpdateDTO);

        return administration != null
                ? new ResponseEntity<>(administration, HttpStatusCode.valueOf(200))
                : new ResponseEntity<>(HttpStatusCode.valueOf(406));
    }

}
