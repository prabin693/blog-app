package com.blog.blogapp.controllers;


import com.blog.blogapp.payloads.ApiResponse;
import com.blog.blogapp.payloads.UserDto;
import com.blog.blogapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins="http://localhost:3000", allowCredentials = "true", allowedHeaders = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    //Post - create user
    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
     UserDto createdUserDto = this.userService.createUser(userDto);
     return new ResponseEntity <> (createdUserDto, HttpStatus.CREATED);

    }

    //put - update user
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable Integer id){
        UserDto updatedUserDto = this.userService.updateUser(userDto, id);
        return new ResponseEntity <> (updatedUserDto, HttpStatus.OK);
    }



    //delete - delete user

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id){
        this.userService.deleteUser(id);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "User deleted successfully"), HttpStatus.OK);
    }

    //get - get all users
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return new ResponseEntity<>(this.userService.getAllUsers(), HttpStatus.OK);
    }


    //get - get user by id
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer id){
        UserDto userDtoGet = this.userService.getUserById(id);
        return new ResponseEntity<>(userDtoGet, HttpStatus.OK);
    }

}


