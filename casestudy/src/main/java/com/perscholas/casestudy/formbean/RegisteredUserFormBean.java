package com.perscholas.casestudy.formbean;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class RegisteredUserFormBean {

    private Integer id;


    @Length(min=8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "Password must contain at least one lowercase letter, one uppercase letter, and one digit")
    private String password;

    @Email( message = "Email must be valid")
    @NotEmpty( message ="Email address cannnot be empty")
    private String email;

    @NotEmpty( message ="confirmPassword cannnot be empty")
    private String confirmPassword;


}
