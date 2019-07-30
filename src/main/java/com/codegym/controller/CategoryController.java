package com.codegym.controller;

import com.codegym.model.Category;
import com.codegym.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/categorys/")
    public ResponseEntity<Iterable<Category>> findAll(){
        Iterable<Category> categories = categoryService.findAllCategory();
        if (categories == null){
            return new ResponseEntity<Iterable<Category>>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<Iterable<Category>>(categories, HttpStatus.OK);
        }
    }

    @PostMapping("/categorys/")
    public ResponseEntity<Void> createBlog(@RequestBody Category category, UriComponentsBuilder ucBuilder){
        System.out.println("Creating category");
        categoryService.save(category);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/categorys/{id}").buildAndExpand(category.getId()).toUri());
        return new ResponseEntity<Void>(headers,HttpStatus.CREATED);
    }

    @PutMapping("/categorys/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable("id") Long id, @RequestBody Category category){
        Category categoryFind = categoryService.findById(id);
        if (categoryFind == null){
            System.out.println("Customer with id " + id + " not found");
            return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
        }else {
            categoryFind.setId(category.getId());
            categoryFind.setCategory(category.getCategory());
            categoryFind.setBlogs(category.getBlogs());
            categoryService.save(categoryFind);
            return new ResponseEntity<Category>(categoryFind,HttpStatus.OK);
        }
    }
    @DeleteMapping("/categorys/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") Long id){
        Category category = categoryService.findById(id);
        if (category==null){
            System.out.printf("Not found");
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }else {
            categoryService.delete(category.getId());
            System.out.printf("Delete success");
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
    }

    @GetMapping("/categorys/{id}")
    public ResponseEntity<Category> detailCategory(@PathVariable("id") Long id){
        Category category = categoryService.findById(id);
        if (category== null){
            System.out.println("Not found");
            return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<Category>(category, HttpStatus.OK);
        }
    }
}
