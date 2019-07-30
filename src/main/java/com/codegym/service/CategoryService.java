package com.codegym.service;

import com.codegym.model.Category;

import java.util.List;

public interface CategoryService {
    Iterable<Category> findAllCategory();
    Category findById(Long id);
    void save(Category category);
    void delete(Long id);
    Category findByCategory(String category);
}
