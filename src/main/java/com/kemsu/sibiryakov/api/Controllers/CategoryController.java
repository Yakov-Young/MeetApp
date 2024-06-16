package com.kemsu.sibiryakov.api.Controllers;

import com.kemsu.sibiryakov.api.DTOs.CategoryDTO.CategoryAddOneDTO;
import com.kemsu.sibiryakov.api.DTOs.CategoryDTO.CategoryAddManyDTO;
import com.kemsu.sibiryakov.api.Entities.Category;
import com.kemsu.sibiryakov.api.Entities.Emuns.ERole;
import com.kemsu.sibiryakov.api.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kemsu.sibiryakov.api.Services.RightsService.checkRight;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAll(@CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.ADMINISTRATOR)) {
            List<Category> categories = categoryService.getAllCategory();

            return !categories.isEmpty()
                    ? new ResponseEntity<>(categories, HttpStatusCode.valueOf(201))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(404));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Category> addOneCategory(@RequestBody CategoryAddOneDTO categoryAddOneDTO,
                                                   @CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.ADMINISTRATOR)) {

            Category category = categoryService.createOneCategory(categoryAddOneDTO);

            return category != null
                    ? new ResponseEntity<>(category, HttpStatusCode.valueOf(201))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(400));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @PostMapping("/addMany")
    public ResponseEntity<List<Category>> addManyCategory(@RequestBody CategoryAddManyDTO categoryAddManyDTO,
                                                          @CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.ADMINISTRATOR)) {

            List<Category> categories = categoryService.createManyCategory(categoryAddManyDTO);

            return categories != null
                    ? new ResponseEntity<>(categories, HttpStatusCode.valueOf(201))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(400));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Ok")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Long id,
                                                @CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.ADMINISTRATOR)) {
            categoryService.deleteOneCategory(id);

            return new ResponseEntity<>(HttpStatusCode.valueOf(200));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }
}
