package com.kemsu.sibiryakov.api.Controllers;

import com.kemsu.sibiryakov.api.Entities.UserPart.OrganizerPhoneNumber;
import com.kemsu.sibiryakov.api.Services.OrganizerPhoneNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/phone")
public class PhoneController {
    private final OrganizerPhoneNumberService organizerPhoneNumberService;
    @Autowired
    public PhoneController(OrganizerPhoneNumberService organizerPhoneNumberService) {
        this.organizerPhoneNumberService = organizerPhoneNumberService;
    }

    @GetMapping("/org/all")
    @ResponseStatus(HttpStatus.OK)
    public List<OrganizerPhoneNumber> getAllOrganizerNumberPhone() {
        return organizerPhoneNumberService.getAllPhone();
    }
}
