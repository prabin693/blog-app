package com.blog.blogapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PathConfig {
    @Bean
    public String path() {
     return "C:\\Users\\User\\Desktop\\blogapp\\src\\main\\resources\\static\\images\\";
    }
}
