package com.blog.blogapp.payloads;

import com.blog.blogapp.entities.Comment;
import lombok.Getter;
import lombok.Setter;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
public class PostDto {

    private String title;
    private String content;
    private String imageName;
    private Date addedDate;
    private CategoryDto category;
    private UserDto user;
    private Set<CommentDto> comments = new HashSet<>();

    public static void setImageName(String imageName ) {

    }
}
