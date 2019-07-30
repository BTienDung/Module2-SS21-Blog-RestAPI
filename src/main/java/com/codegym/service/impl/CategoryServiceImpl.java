package com.codegym.service.impl;

import com.codegym.model.Category;
import com.codegym.repository.CategoryRepository;
import com.codegym.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public Iterable<Category> findAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findOne(id);
    }

    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void delete(Long id) {
        Category category = categoryRepository.findOne(id);
        categoryRepository.delete(category);
    }

    @Override
    public Category findByCategory(String category) {
        return categoryRepository.findByCategory(category);
    }
}
