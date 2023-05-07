package com.blog.blogapp.payloads;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private Integer id;

    @NotEmpty(message = "Username cannot be null")
    @Size(min = 3, max = 15, message = "Username must be between 3 and 15 characters")
    private String name;

    @Email
    @NotEmpty(message = "Email cannot be null")
    private String email;

    @NotEmpty(message = "Password cannot be null")
    @Size(min = 3, max = 10, message = "Password must be between 3 and 10 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$", message = "Password must contain at least one uppercase, one lowercase, one digit and one special character")

    private String password;

    @NotEmpty
    private String about;

}
