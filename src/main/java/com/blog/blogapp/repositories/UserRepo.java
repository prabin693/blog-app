package com.blog.blogapp.repositories;

import com.blog.blogapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {

}

