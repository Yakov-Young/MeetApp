package com.kemsu.sibiryakov.api.Controllers;

import com.kemsu.sibiryakov.api.DTOs.CategoryDTO.CategoryAddOneDTO;
import com.kemsu.sibiryakov.api.DTOs.CategoryDTO.CategoryAddManyDTO;
import com.kemsu.sibiryakov.api.Entities.Category;
import com.kemsu.sibiryakov.api.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Category> getAll() {
        return categoryService.getAllCategory();
    }

    @PostMapping("/add")
    public ResponseEntity<Category> addOneCategory(@RequestBody CategoryAddOneDTO categoryAddOneDTO) {
        Category category = categoryService.createOneCategory(categoryAddOneDTO);

        return category != null
                ? new ResponseEntity<>(category, HttpStatusCode.valueOf(201))
                : new ResponseEntity<>(HttpStatusCode.valueOf(400));
    }

    @PostMapping("/addMany")
    public ResponseEntity<List<Category>> addManyCategory(@RequestBody CategoryAddManyDTO categoryAddManyDTO) {
        List<Category> categories = categoryService.createManyCategory(categoryAddManyDTO);

        return categories != null
                ? new ResponseEntity<>(categories, HttpStatusCode.valueOf(201))
                : new ResponseEntity<>(HttpStatusCode.valueOf(400));
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Ok")
    public void deleteCategoryById(@PathVariable Long id) {
        categoryService.deleteOneCategory(id);
    }
}
