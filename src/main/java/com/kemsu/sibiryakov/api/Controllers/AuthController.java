package com.kemsu.sibiryakov.api.Controllers;

import com.kemsu.sibiryakov.api.DTOs.RegisterDTO.OrganizerRegisterDTO;
import com.kemsu.sibiryakov.api.DTOs.RegisterDTO.UserRegisterDTO;
import com.kemsu.sibiryakov.api.Entities.UserPart.Organizer;
import com.kemsu.sibiryakov.api.Entities.UserPart.User;
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
@RequestMapping("/api/register")
public class AuthController {
    private final UserService userService;
    private final OrganizerService organizerService;
    @Autowired
    public AuthController(UserService userService, OrganizerService organizerService) {
        this.userService = userService;
        this.organizerService = organizerService;
    }

    @PostMapping("/visitor")
    public User editVisitor(@RequestBody UserRegisterDTO userRegisterDTO) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return userService.createVisitor(
                userService.convertToUser(userRegisterDTO)
        );

        //return userService.createVisitor(userRegisterDTO);

    }

    @PostMapping("/organizer")
    public Organizer editOrganizer(@RequestBody OrganizerRegisterDTO organizerRegisterDTO) {
        return organizerService.createOrganizer(organizerRegisterDTO);

    }
}
