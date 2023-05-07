package com.blog.blogapp.services;

import com.blog.blogapp.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {

    //create
    public CategoryDto createCategory(CategoryDto categoryDto);

    //update

    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

    //delete
    public void deleteCategory(Integer categoryId);


    //get
    public CategoryDto getCategoryById(Integer categoryId);

    //get all
    List<CategoryDto> getAllCategories();
}
