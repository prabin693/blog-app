package com.blog.blogapp.controllers;


import com.blog.blogapp.payloads.JwtAuthRequest;
import com.blog.blogapp.payloads.JwtAuthResponse;
import com.blog.blogapp.security.JwtTokenHelper;
import com.blog.blogapp.services.UserService;
import com.blog.blogapp.services.impl.Roleimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
//@CrossOrigin("*")
@RequestMapping("/api/v1/auth/")
public class AuthController {
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private Roleimpl roleimpl;

    @PostMapping("/login")
    public ResponseEntity<?> createToken(@RequestBody JwtAuthRequest request){
        authenticate(request.getUsername(), request.getPassword());


        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
        //load user roles
        String role = this.roleimpl.findByName(request.getUsername());
        String token = this.jwtTokenHelper.generateToken(userDetails);
        return new ResponseEntity<>(new JwtAuthResponse(token,role), HttpStatus.OK);
    }

    private void authenticate(String username, String password) {
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
    try{
        this.authenticationManager.authenticate(authenticationToken);
    }
    catch (BadCredentialsException e){
    System.out.println("Invalid Credentials");
    throw new BadCredentialsException("Invalid Credentials");
    }
    }

}
