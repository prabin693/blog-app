package com.blog.blogapp.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

    private Integer categoryId;

    @NotEmpty(message = "Category title cannot be null")
    @Size(min = 3, max = 15, message = "Category title must be between 3 and 15 characters")
    private String categoryTitle;

    @NotEmpty(message = "Category description cannot be null")
    private String categoryDescription;
}
