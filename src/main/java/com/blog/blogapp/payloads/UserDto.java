package com.blog.blogapp.payloads;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GeneratorType;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

//    @Size(min = 3, max = 15, message = "Username must be between 3 and 15 characters")
    private String name;

    @Email
    @NotEmpty(message = "Email cannot be null")
    private String email;

    @NotEmpty(message = "Password cannot be null")
//    @Size(min = 3, max = 10, message = "Password must be between 3 and 10 characters")
//    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$", message = "Password must contain at least one uppercase, one lowercase, one digit and one special character")

    private String password;

//    @NotEmpty
    private String about;

}
