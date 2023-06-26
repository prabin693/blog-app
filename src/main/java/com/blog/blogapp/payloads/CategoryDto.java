package com.blog.blogapp.payloads;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer categoryId;

    @NotEmpty(message = "Category title cannot be null")
    @Size(min = 3, max = 15, message = "Category title must be between 3 and 15 characters")
    private String categoryTitle;

    @NotEmpty(message = "Category description cannot be null")
    private String categoryDescription;
}
