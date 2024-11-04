package com.i2i.smrb.controller;

import com.i2i.smrb.dto.LoginDto;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import com.i2i.smrb.dto.CreateUserDto;
import com.i2i.smrb.dto.ResponseMessage;
import com.i2i.smrb.dto.UserResponseDto;
import com.i2i.smrb.service.UserService;

@RestController
@RequestMapping("/auth/users")
public class UserController {
    @Autowired
    private UserService userService;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * <p>
     * This method is used to register the user to the application.
     * After successfully registration, account generated for the user.
     * </p>
     *
     * @param createUserDto {@link CreateUserDto}
     * @return userResponse {@link UserResponseDto}
     */
    @PostMapping("/signup")
    public ResponseEntity<?> userSignUp(@Valid @RequestBody CreateUserDto createUserDto) {
        UserResponseDto userResponse = userService.userSignUp(createUserDto);
        if (ObjectUtils.isEmpty(userResponse)) {
            logger.error("User already exist with email {} Login to Proceed", createUserDto.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseMessage("Customer Already Exist with email " + createUserDto.getEmail() + " Login to Proceed"));
        }
        logger.info("Customer successfully signed up with email {}", userResponse.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    /**
     * <p>
     * This method is used to login the user.
     * After successfully validated JWT token generated is returned.
     * </p>
     *
     * @param loginDto {@link LoginDto}
     * @return ResponseMessage {@link ResponseMessage}
     */
    @PostMapping("/login")
    public ResponseEntity<?> Login(@Valid @RequestBody LoginDto loginDto) {
        ResponseMessage response = userService.authenticateUserWithJWT(loginDto);
        logger.info("Token successfully generated for user name {}", loginDto.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
