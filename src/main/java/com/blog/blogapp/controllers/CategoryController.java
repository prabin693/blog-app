package com.blog.blogapp.controllers;
import com.blog.blogapp.payloads.ApiResponse;
import com.blog.blogapp.payloads.CategoryDto;
import com.blog.blogapp.services.CategoryService;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/category")
public class CategoryController  {

    @Autowired
    private CategoryService categoryService;



    //create
    @CrossOrigin("*")
    @PostMapping("/create")

    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
       CategoryDto createCategory= this.categoryService.createCategory(categoryDto);
         return new ResponseEntity<>(createCategory, HttpStatus.CREATED);
    }

    //update
@PutMapping("/update/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer id){
        CategoryDto updateCategory= this.categoryService.updateCategory(categoryDto, id);
        return new ResponseEntity<CategoryDto>(updateCategory, HttpStatus.OK);
    }


    //delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer id){
        this.categoryService.deleteCategory(id);
        return ResponseEntity.ok(new ApiResponse(true, "Category deleted successfully"));
    }



    //get
    @GetMapping("/get/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer id){
        CategoryDto getCategory= this.categoryService.getCategoryById(id);
        return new ResponseEntity<CategoryDto>(getCategory, HttpStatus.OK);
    }



    //get all
    @GetMapping("/get/all")
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        List<CategoryDto> getAllCategories= this.categoryService.getAllCategories();
        return new ResponseEntity<List<CategoryDto>>(getAllCategories, HttpStatus.OK);
    }
}
