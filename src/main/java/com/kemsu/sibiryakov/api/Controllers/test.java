package com.kemsu.sibiryakov.api.Controllers;

import com.kemsu.sibiryakov.api.JwtFilter.JwtFilter;
import com.kemsu.sibiryakov.api.Services.AccessService;
import com.kemsu.sibiryakov.api.Services.OrganizerPhoneNumberService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/test")
public class test {
    private final AccessService accessService;
    private final OrganizerPhoneNumberService organizerPhoneNumberService;


    @Autowired
    public test(AccessService accessService, OrganizerPhoneNumberService organizerPhoneNumberService) {
        this.accessService = accessService;
        this.organizerPhoneNumberService = organizerPhoneNumberService;
    }

    @GetMapping("/1")
    public ResponseEntity<String> test(@CookieValue(value = "jwt") String jwt) throws Exception {
        try {
            Claims claims = JwtFilter.getBody(jwt);
            return new ResponseEntity<>(claims.get("role") + " " + claims.getId() + " " + claims.get("id"), HttpStatusCode.valueOf(202));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @DeleteMapping("/tel/{id}")
    public void delPhone(@PathVariable Long id,
                         @CookieValue(value = "jwt") String jwt) throws Exception {
        organizerPhoneNumberService.deleteById(id);
    }

}
