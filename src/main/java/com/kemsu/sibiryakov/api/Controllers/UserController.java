package com.kemsu.sibiryakov.api.Controllers;

import com.kemsu.sibiryakov.api.DTOs.UpdateDTO.UserUpdateDTO;
import com.kemsu.sibiryakov.api.Entities.Emuns.Gender;
import com.kemsu.sibiryakov.api.Entities.UserPart.User;
import com.kemsu.sibiryakov.api.JwtFilter.JwtFilter;
import com.kemsu.sibiryakov.api.Services.CategoryService;
import com.kemsu.sibiryakov.api.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getById(id);

        return user != null
                ? new ResponseEntity<>(user, HttpStatusCode.valueOf(200))
                : new ResponseEntity<>(HttpStatusCode.valueOf(404));
    }

    @PostMapping("/update")
    public User updateUserProfile(@RequestBody UserUpdateDTO userUpdateDTO,
                                  @CookieValue("jwt") String jwt) {

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

        return userService.save(user);
    }
}
