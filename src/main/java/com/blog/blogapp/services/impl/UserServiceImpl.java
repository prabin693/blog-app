package com.blog.blogapp.services.impl;

import com.blog.blogapp.entities.User;
import com.blog.blogapp.exceptions.ResourceNotFoundException;
import com.blog.blogapp.payloads.UserDto;
import com.blog.blogapp.repositories.UserRepo;
import com.blog.blogapp.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private   UserRepo userRepo;


    @Autowired
    private ModelMapper modelMapper;


    public UserDto createUser(UserDto userDto) {
      User user = this.DtoToUser(userDto);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
      User savedUser = this.userRepo.save(user);
        return this.UserToDto(savedUser);
    }

    
    public UserDto getUserById(  Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User" , "id" , userId));
        return this.UserToDto(user);

    }

    
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> {
            return new ResourceNotFoundException("User", "id", userId);
        });
//        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
//        user.setAbout(userDto.getAbout());
       User updateUser = this.userRepo.save(user);
       UserDto userDto1 = this.UserToDto(updateUser);
         return userDto1;

    }

    
    public List<UserDto> getAllUsers() {
       List<User> users = this.userRepo.findAll();
       List <UserDto> userDtos = users.stream().map(user->this.UserToDto(user)).collect(Collectors.toList());
       return userDtos;
    }

    
    public void deleteUser(Integer   userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> {
            return new ResourceNotFoundException("User", "id", userId);
        });
        this.userRepo.delete(user);


    }

    private User DtoToUser(UserDto userDto){
        //        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        user.setAbout(userDto.getAbout());
        return this.modelMapper.map(userDto, User.class);
    }

    private UserDto UserToDto(User user){
        //        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
//        userDto.setAbout(user.getAbout());
        return this.modelMapper.map(user, UserDto.class);
    }
}
