package com.codegym.service;

import com.codegym.model.Blog;
import com.codegym.model.Category;
import org.springframework.data.domain.Page;

public interface BlogService {
    Iterable<Blog> findAllBlog();
    Blog findById(Long id);
    void save(Blog blog);
    void delete(Long id);
    Iterable<Blog> findAllByCategory(Category category);
}
