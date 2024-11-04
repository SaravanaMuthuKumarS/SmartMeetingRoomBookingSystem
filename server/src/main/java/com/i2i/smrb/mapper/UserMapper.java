package com.i2i.smrb.mapper;

import com.i2i.smrb.dto.UserResponseDto;
import org.mindrot.jbcrypt.BCrypt;

import com.i2i.smrb.dto.CreateUserDto;
import com.i2i.smrb.model.User;

public class UserMapper {
    public User createUser(CreateUserDto createUserDto) {
        String salt = BCrypt.gensalt();
        return User.builder()
                .email(createUserDto.getEmail())
                .hashedPassword(BCrypt.hashpw(createUserDto.getPassword(), salt))
                .projectName(createUserDto.getProjectName())
                .role(createUserDto.getRole())
                .build();
    }

    public UserResponseDto createUserResponseDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .projectName(user.getProjectName())
                .build();
    }
}
