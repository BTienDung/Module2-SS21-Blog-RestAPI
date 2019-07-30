package com.codegym.controller;

import com.codegym.model.Blog;
import com.codegym.model.Category;
import com.codegym.service.BlogService;
import com.codegym.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class BlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/blogs/")
    public ResponseEntity<Iterable<Blog>> findAll(){
        Iterable<Blog> blogs = blogService.findAllBlog();
        if (blogs == null){
            return new ResponseEntity<Iterable<Blog>>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<Iterable<Blog>>(blogs, HttpStatus.OK);
        }
    }

    @PostMapping("/blogs/")
    public ResponseEntity<Void> createBlog(@RequestBody Blog blog, UriComponentsBuilder ucBuilder){
        System.out.println("Creating Blog.");
        blogService.save(blog);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/blogs/{id}").buildAndExpand(blog.getId()).toUri());
        return new ResponseEntity<Void>(headers,HttpStatus.CREATED);
    }

    @PutMapping("/blogs/{id}")
    public ResponseEntity<Blog> updateBlog(@PathVariable("id") Long id, @RequestBody Blog blog){
        Blog blogF = blogService.findById(id);
        if (blogF == null){
            return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
        }else {
            blogF.setId(blog.getId());
            blogF.setName(blog.getName());
            blogF.setCategory(blog.getCategory());
            blogF.setContent(blog.getContent());
            blogService.save(blogF);
            return new ResponseEntity<Blog>(blogF, HttpStatus.OK);
        }
    }
    @GetMapping("/blogs/{id}")
    public ResponseEntity<Blog> detail(@PathVariable("id") Long id){
        Blog blog = blogService.findById(id);
        if (blog == null){
            System.out.printf("Not found");
            return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<Blog>(blog, HttpStatus.OK);
        }
    }

    @DeleteMapping("/blogs/{id}")
    public ResponseEntity<Blog> delete(@PathVariable("id") Long id){
        Blog blog = blogService.findById(id);
        blogService.delete(blog.getId());
        if (blog == null){
            System.out.printf("Not found");
            return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<Blog>(blog, HttpStatus.OK);
        }
    }
    @GetMapping("/search/")
    public ResponseEntity<Iterable<Blog>> findByCategory(@RequestParam("category") String category){
        Category categoryFind = categoryService.findByCategory(category);
        Iterable<Blog> blogs = blogService.findAllByCategory(categoryFind);
        return new ResponseEntity<Iterable<Blog>>(blogs, HttpStatus.OK);
    }
}
