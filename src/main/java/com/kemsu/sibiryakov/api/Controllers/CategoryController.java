package com.kemsu.sibiryakov.api.Controllers;

import com.kemsu.sibiryakov.api.DTOs.CategoryDTO.CategoryAddOneDTO;
import com.kemsu.sibiryakov.api.DTOs.CategoryDTO.CategoryAddManyDTO;
import com.kemsu.sibiryakov.api.Entities.Category;
import com.kemsu.sibiryakov.api.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public List<Category> getAll() {
        return categoryService.getAllCategory();
    }

    @PostMapping("/add")
    public Category addOneCategory(@RequestBody CategoryAddOneDTO categoryAddOneDTO) {
        System.out.print(categoryAddOneDTO.getName());
        return categoryService.createOneCategory(categoryAddOneDTO);
    }

    @PostMapping("/addMany")
    public List<Category> addManyCategory(@RequestBody CategoryAddManyDTO categoryAddManyDTO) {
        return categoryService.createManyCategory(categoryAddManyDTO);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Ok")
    public void deleteCategoryById(@PathVariable Long id) {
        categoryService.deleteOneCategory(id);
    }
}
