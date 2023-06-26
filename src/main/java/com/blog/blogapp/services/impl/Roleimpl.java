package com.blog.blogapp.services.impl;

import com.blog.blogapp.entities.User;
import com.blog.blogapp.exceptions.ResourceNotFoundException;
import com.blog.blogapp.repositories.RoleRepo;
import com.blog.blogapp.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Roleimpl {

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    public Roleimpl(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Autowired
    private UserRepo userRepo;

    public String findByName(String email) {
        User user = this.userRepo.findByEmail(email).orElseThrow(() -> {
            return new ResourceNotFoundException("User", "email", 1);
          }

          );
        return user.getRole();
    }
}