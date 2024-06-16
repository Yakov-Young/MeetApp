package com.kemsu.sibiryakov.api.Controllers;


import com.kemsu.sibiryakov.api.DTOs.UpdateDTO.AdministrationUpdateDTO;
import com.kemsu.sibiryakov.api.DTOs.UpdateDTO.OrganizerUpdateDTO;
import com.kemsu.sibiryakov.api.Entities.Emuns.ERole;
import com.kemsu.sibiryakov.api.Entities.UserPart.Administration;
import com.kemsu.sibiryakov.api.JwtFilter.JwtFilter;
import com.kemsu.sibiryakov.api.Services.AdministrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.kemsu.sibiryakov.api.Services.RightsService.checkRight;

@RestController
@RequestMapping("/api/administration")
public class AdministrationController {
    public final AdministrationService administrationService;

    @Autowired
    public AdministrationController(AdministrationService administrationService) {
        this.administrationService = administrationService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Administration>> getAll(@CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.MODERATOR, ERole.ADMINISTRATOR)) {
            List<Administration> administrations = administrationService.getAll();

            return administrations != null
                    ? new ResponseEntity<>(administrations, HttpStatusCode.valueOf(200))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(404));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Administration> getById(@PathVariable Long id,
                                                  @CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.USER, ERole.MODERATOR, ERole.ADMINISTRATOR, ERole.ORGANIZER, ERole.ADMINISTRATION)) {
            Administration administration = administrationService.getById(id);
            return administration != null
                    ? new ResponseEntity<>(administration, HttpStatusCode.valueOf(200))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(404));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @PostMapping("/update")
    public ResponseEntity<Administration> updateAdministrationProfile(@RequestBody AdministrationUpdateDTO administrationUpdateDTO,
                                                                      @CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.ADMINISTRATION)) {
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
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

}
