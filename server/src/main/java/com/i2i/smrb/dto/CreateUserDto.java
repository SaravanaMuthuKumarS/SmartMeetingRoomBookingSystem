package com.i2i.smrb.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.i2i.smrb.enums.UserRole;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {
    @NotBlank(message = "Email should not be blank")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "Invalid Email Id format")
    private String email;

    @NotBlank(message = "Password should not be empty")
    private String password;

    private UserRole role;

    @NotBlank(message = "Project name should not be blank")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Project name accepts only a-z A-Z")
    private String projectName;
}
