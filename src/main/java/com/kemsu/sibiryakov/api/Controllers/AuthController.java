package com.kemsu.sibiryakov.api.Controllers;

import com.kemsu.sibiryakov.api.DTOs.AccessDTO.AccessDTO;
import com.kemsu.sibiryakov.api.DTOs.RegisterDTO.AdministrationRegisterDTO;
import com.kemsu.sibiryakov.api.DTOs.RegisterDTO.OrganizerRegisterDTO;
import com.kemsu.sibiryakov.api.DTOs.RegisterDTO.UserRegisterDTO;
import com.kemsu.sibiryakov.api.Entities.Emuns.UserStatus;
import com.kemsu.sibiryakov.api.Entities.Interface.IUser;
import com.kemsu.sibiryakov.api.Entities.UserPart.*;
import com.kemsu.sibiryakov.api.JwtFilter.JwtFilter;
import com.kemsu.sibiryakov.api.Services.AccessService;
import com.kemsu.sibiryakov.api.Services.AdministrationService;
import com.kemsu.sibiryakov.api.Services.OrganizerService;
import com.kemsu.sibiryakov.api.Services.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AccessService accessService;

    private final UserService userService;
    private final OrganizerService organizerService;
    private final AdministrationService administrationService;


    private static final String JWT_KEY = "CGuZtKUBu3JIvXOCwWIyJYSS4cP+TNiDIDdvhr6aqnpQ45y3nw0qC9WY4cJPiHXcKKKlILZhpJI8hX5MTRn9QQ==";
    @Autowired
    public AuthController(AccessService accessService, UserService userService,
                          OrganizerService organizerService, AdministrationService administrationService) {
        this.accessService = accessService;
        this.userService = userService;
        this.organizerService = organizerService;
        this.administrationService = administrationService;
    }

    @PostMapping("/login")
    public ResponseEntity<IUser> login(@RequestBody AccessDTO accessDTO, HttpServletResponse response) {
        IUser user = null;

        try {
            Access access = accessService.getByLogin(accessDTO.getLogin());
            String role = null;

            if (access != null) {
                user = userService.getByAccess(access);
            }
            if (user == null) {
                user = organizerService.getByAccess(access);
            }
            if (user == null) {
                user = administrationService.getByAccess(access);
            }

            if (user.getClass() == User.class) {
                if (Objects.equals(((User) user).getStatus().getStatus(), UserStatus.BANNED.getState())) {
                    return null;
                }

                role = ((User) user).getRole().getName();
            } else if (user.getClass() == Organizer.class) {
                if (Objects.equals(((Organizer) user).getStatus().getStatus(), UserStatus.BANNED.getState())) {
                    return null;
                }

                role = "organizer";
            } else {
                role = "administration";
            }

            SecretKey key = Keys.hmacShaKeyFor(
                    Decoders.BASE64.decode(JWT_KEY)
            );

            String jwt = Jwts.builder()
                    .setIssuer(String.valueOf(user.getId()))
                    .setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000))
                    .setClaims(Map.of("role", role))
                    .claim("role", role)
                    .claim("id", user.getId())
                    .signWith(key, SignatureAlgorithm.HS512)
                    .compact();

            Cookie cookie = new Cookie("jwt", jwt);
            cookie.setHttpOnly(true);
            cookie.setPath("/api/");

            response.addCookie(cookie);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(401));
        }

        return new ResponseEntity<>(user, HttpStatusCode.valueOf(200));
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
