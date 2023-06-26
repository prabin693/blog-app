package com.blog.blogapp.payloads;

import lombok.Data;

@Data
public class JwtAuthRequest {

    public String username;
    public String password;
}
