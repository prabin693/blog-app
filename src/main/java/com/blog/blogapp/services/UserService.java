package com.blog.blogapp.services;

import com.blog.blogapp.payloads.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);
    UserDto getUserById(Integer id);
    UserDto updateUser(UserDto userDto ,Integer id);
    List<UserDto> getAllUsers();
    void deleteUser(Integer id);



}
