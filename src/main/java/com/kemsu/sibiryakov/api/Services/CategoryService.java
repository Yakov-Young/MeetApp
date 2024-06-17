package com.kemsu.sibiryakov.api.Services;

import com.kemsu.sibiryakov.api.DTOs.CategoryDTO.CategoryAddManyDTO;
import com.kemsu.sibiryakov.api.DTOs.CategoryDTO.CategoryAddOneDTO;
import com.kemsu.sibiryakov.api.Entities.Category;
import com.kemsu.sibiryakov.api.Repositories.ICategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CategoryService {
    private final ICategoriesRepository categoriesRepository;

    @Autowired
    public CategoryService(ICategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    public List<Category> getAllCategory() {
        return categoriesRepository.findAll();
    }

    public Category getById(Long id) {
        return categoriesRepository.findById(id).orElse(null);
    }

    public Set<Category> getByManyId(List<Long> ids) {
        Set<Category> categories = new HashSet<>();
        for (Long id : ids) {
            categories.add(
                    this.getById(id)
            );
        }

        return categories;
    }

    public Category createOneCategory(CategoryAddOneDTO categoryDTO) {
        Category category = new Category(categoryDTO.getName());

        return categoriesRepository.save(category);
    }

    public List<Category> createManyCategory(CategoryAddManyDTO categoryManyOneDTO) {
        List<Category> categories = new ArrayList<>();

        for (String n : categoryManyOneDTO.getName()) {
            categories.add(new Category(n));
        }
        return categoriesRepository.saveAll(categories);
    }

    public void deleteOneCategory(Long id) {
        categoriesRepository.deleteById(id);
    }
}
