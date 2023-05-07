package com.blog.blogapp.repositories;

import com.blog.blogapp.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
