package com.blog.blogapp.services.impl;

import com.blog.blogapp.entities.Category;
import com.blog.blogapp.exceptions.ResourceNotFoundException;
import com.blog.blogapp.payloads.CategoryDto;
import com.blog.blogapp.repositories.CategoryRepo;
import com.blog.blogapp.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;



    @Autowired
    private ModelMapper modelMapper;

    
    public CategoryDto createCategory(CategoryDto categoryDto) {
       Category cat= this.modelMapper.map(categoryDto, Category.class);
       Category addCat =this.categoryRepo.save(cat);
       return this.modelMapper.map(addCat, CategoryDto.class);
    }

    
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category cat= this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category not found","id", categoryId));
        cat.setCategoryTitle(categoryDto.getCategoryTitle());
        cat.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updatedCat = this.categoryRepo.save(cat);
        return this.modelMapper.map(updatedCat, CategoryDto.class);
    }

    
    public void deleteCategory(Integer categoryId) {
        Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category not found","id", categoryId));
        this.categoryRepo.delete(cat);
    }

    
    public CategoryDto getCategoryById(Integer categoryId) {
        Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category not found","id", categoryId));
        return this.modelMapper.map(cat, CategoryDto.class);

    }

    
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = this.categoryRepo.findAll();
      categories.stream().map((cat)->this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
        return categories.stream().map((cat)->this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
    }




}
