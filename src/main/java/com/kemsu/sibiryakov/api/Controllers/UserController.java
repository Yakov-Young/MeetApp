package com.kemsu.sibiryakov.api.Controllers;

import com.kemsu.sibiryakov.api.DTOs.BanDTO;
import com.kemsu.sibiryakov.api.DTOs.UpdateDTO.UserUpdateDTO;
import com.kemsu.sibiryakov.api.Entities.Emuns.ERole;
import com.kemsu.sibiryakov.api.Entities.Emuns.Gender;
import com.kemsu.sibiryakov.api.Entities.UserPart.User;
import com.kemsu.sibiryakov.api.JwtFilter.JwtFilter;
import com.kemsu.sibiryakov.api.Services.CategoryService;
import com.kemsu.sibiryakov.api.Services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kemsu.sibiryakov.api.Services.RightsService.checkRight;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final CategoryService categoryService;

    @Autowired
    public UserController(UserService userService, CategoryService categoryService) {
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<User>> getAll(@CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.MODERATOR, ERole.ADMINISTRATOR)) {
            List<User> users = userService.getAll();

            return !users.isEmpty()
                    ? new ResponseEntity<>(users, HttpStatusCode.valueOf(200))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(400));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id,
                                            @CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.USER, ERole.MODERATOR, ERole.ADMINISTRATOR, ERole.ORGANIZER, ERole.ADMINISTRATION)) {
            User user = userService.getById(id);

            return user != null
                    ? new ResponseEntity<>(user, HttpStatusCode.valueOf(200))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(404));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @GetMapping("/allBanned")
    public ResponseEntity<List<User>> getBannedUser(@CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.MODERATOR, ERole.ADMINISTRATOR)) {
            List<User> bannedVisitors = userService.getBannedVisitors();

            return !bannedVisitors.isEmpty()
                    ? new ResponseEntity<>(bannedVisitors, HttpStatusCode.valueOf(200))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(404));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @GetMapping("/allActive")
    public ResponseEntity<List<User>> getActiveUser(@CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.MODERATOR, ERole.ADMINISTRATOR)) {
            List<User> activeVisitors = userService.getActiveVisitors();

            return !activeVisitors.isEmpty()
                    ? new ResponseEntity<>(activeVisitors, HttpStatusCode.valueOf(200))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(404));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @GetMapping("/allWarring")
    public ResponseEntity<List<User>> getWarringUser(@CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.MODERATOR, ERole.ADMINISTRATOR)) {
            List<User> warringVisitors = userService.getWarringVisitors();

            return !warringVisitors.isEmpty()
                    ? new ResponseEntity<>(warringVisitors, HttpStatusCode.valueOf(200))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(404));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @GetMapping("/allDeleted")
    public ResponseEntity<List<User>> getDeletedUser(@CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.MODERATOR, ERole.ADMINISTRATOR)) {
            List<User> deletedVisitors = userService.getDeletedVisitors();

            return !deletedVisitors.isEmpty()
                    ? new ResponseEntity<>(deletedVisitors, HttpStatusCode.valueOf(200))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(404));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @GetMapping("/all/moderators")
    public ResponseEntity<List<User>> getModerators(@CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.ADMINISTRATOR)) {
            List<User> moderators = userService.getModerators();

            return !moderators.isEmpty()
                    ? new ResponseEntity<>(moderators, HttpStatusCode.valueOf(200))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(404));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @PostMapping("/selfDelete")
    public ResponseEntity<User> deleteUser(@CookieValue(value = "jwt", required = false) String jwt,
                                           HttpServletResponse response) {
        if (checkRight(jwt, ERole.USER)) {
            Long userId = Long.parseLong(
                    JwtFilter.getBody(jwt)
                            .get("id")
                            .toString()
            );

            User user = userService.deleteUser(userId);

            Cookie cookie = new Cookie("jwt", null);
            cookie.setMaxAge(0);
            cookie.setHttpOnly(true);
            cookie.setPath("/api/");

            response.addCookie(cookie);

            return user != null
                    ? new ResponseEntity<>(user, HttpStatusCode.valueOf(200))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(400));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @PostMapping("/update")
    public ResponseEntity<User> updateUserProfile(@RequestBody UserUpdateDTO userUpdateDTO,
                                                  @CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.USER, ERole.MODERATOR, ERole.ADMINISTRATOR)) {
            User user = userService.getById(
                    Long.parseLong(
                            JwtFilter.getBody(jwt)
                                    .get("id")
                                    .toString()
                    )
            );

            user.setName(userUpdateDTO.getName());
            user.setSurname(userUpdateDTO.getSurname());
            user.setPatronymic(userUpdateDTO.getPatronymic());
            user.setDescription(userUpdateDTO.getDescription());
            user.setBirthday(userUpdateDTO.getBirthday());
            user.setGender(Gender.valueOf(userUpdateDTO.getGender().toUpperCase()));

            user.setCategories(categoryService.getByManyId(userUpdateDTO.getCategory()));

            return new ResponseEntity<>(userService.save(user), HttpStatusCode.valueOf(200));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @PostMapping("/ban")
    public ResponseEntity<User> banUser(@RequestBody BanDTO banDTO,
                                        @CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.MODERATOR, ERole.ADMINISTRATOR)) {
            Long moderId = Long.parseLong(
                    JwtFilter.getBody(jwt)
                            .get("id")
                            .toString()
            );

            User user = userService.banUser(banDTO, moderId);

            return user != null
                    ? new ResponseEntity<>(user, HttpStatusCode.valueOf(200))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(400));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }
}
