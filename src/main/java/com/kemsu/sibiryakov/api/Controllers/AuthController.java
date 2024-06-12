package com.kemsu.sibiryakov.api.Controllers;

import com.kemsu.sibiryakov.api.DTOs.AccessDTO.AccessDTO;
import com.kemsu.sibiryakov.api.DTOs.RegisterDTO.AdministrationRegisterDTO;
import com.kemsu.sibiryakov.api.DTOs.RegisterDTO.OrganizerRegisterDTO;
import com.kemsu.sibiryakov.api.DTOs.RegisterDTO.UserRegisterDTO;
import com.kemsu.sibiryakov.api.Entities.Interface.IUser;
import com.kemsu.sibiryakov.api.Entities.UserPart.*;
import com.kemsu.sibiryakov.api.Services.AccessService;
import com.kemsu.sibiryakov.api.Services.AdministrationService;
import com.kemsu.sibiryakov.api.Services.OrganizerService;
import com.kemsu.sibiryakov.api.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AccessService accessService;

    private final UserService userService;
    private final OrganizerService organizerService;
    private final AdministrationService administrationService;
    @Autowired
    public AuthController(AccessService accessService, UserService userService,
                          OrganizerService organizerService, AdministrationService administrationService) {
        this.accessService = accessService;
        this.userService = userService;
        this.organizerService = organizerService;
        this.administrationService = administrationService;
    }

    @PostMapping("/login")
    public IUser login(@RequestBody AccessDTO accessDTO) {
        Access access = accessService.getByLogin(accessDTO.getLogin());
        IUser result = null;

        if (access != null) {
            result = userService.getByAccess(access);
        }
        if (result == null) {
            result = organizerService.getByAccess(access);
        }
        if (result == null) {
            result = administrationService.getByAccess(access);
        }

        return result;
    }

    @PostMapping("/registerVisitor")
    public User registerVisitor(@RequestBody UserRegisterDTO userRegisterDTO) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return userService.createVisitor(
                userService.prepareToRegisterUser(userRegisterDTO)
        );

        //return userService.createVisitor(userRegisterDTO);

    }

    @PostMapping("/registerOrganizer")
    public Organizer registerOrganizer(@RequestBody OrganizerRegisterDTO organizerRegisterDTO) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return organizerService.createOrganizer(
                organizerService.prepareToRegisterOrganizer(organizerRegisterDTO)
        );

        //return organizerService.createOrganizer(organizerRegisterDTO);

    }

    @PostMapping("/registerAdministration")
    private Administration registerAdministration(@RequestBody AdministrationRegisterDTO administrationRegisterDTO) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return administrationService.createAdministration(
                administrationService.prapareToRegisterAdministration(administrationRegisterDTO)
        );
    }

    @PostMapping("/registerModerator")
    private User registerModerator(@RequestBody UserRegisterDTO moderatorRegisterDTO) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return userService.createModerator(
                userService.prepareToRegisterUser(moderatorRegisterDTO)
        );
    }
}
