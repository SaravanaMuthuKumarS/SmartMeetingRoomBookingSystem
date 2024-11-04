package com.i2i.smrb.service;

import java.sql.Date;
import java.util.Optional;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.i2i.smrb.dto.CreateUserDto;
import com.i2i.smrb.dto.UserResponseDto;
import com.i2i.smrb.mapper.UserMapper;
import com.i2i.smrb.model.User;
import com.i2i.smrb.repository.UserRepository;
import com.i2i.smrb.dto.LoginDto;
import com.i2i.smrb.dto.ResponseMessage;
import com.i2i.smrb.exception.LoginException;
import com.i2i.smrb.exception.SignupException;

@Service
public class UserService {
    @Value("${jwt.secret.key}")
    private String secretKey;

    @Autowired
    private UserRepository userRepository;

    private final UserMapper userMapper = new UserMapper();
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserResponseDto userSignUp(CreateUserDto createUserDto) {
        try {
            User user = userMapper.createUser(createUserDto);
            userRepository.save(user);
            return userMapper.createUserResponseDto(user);
        } catch (Exception e) {
            throw new SignupException("Error Occurred while " +
                    "Creating the user with email : " + createUserDto.getEmail(), e);
        }
    }

    public ResponseMessage authenticateUserWithJWT(LoginDto loginDto) {
        logger.debug("Authenticating user {} with JWT", loginDto.getEmail());
        try {
            Optional<User> user = userRepository.findByEmail(loginDto.getEmail());
            if (user.isEmpty()) {
                throw new LoginException("User Details Does not exist with email : " + loginDto.getEmail() +
                        " Kindly signup to continue");
            }
            User userDetails = user.get();
            if (!BCrypt.checkpw(loginDto.getPassword(), userDetails.getHashedPassword())) {
                throw new LoginException("User password doesn't match for email " + loginDto.getEmail());
            }
            String token = Jwts.builder()
                    .claim("Role", userDetails.getRole())
                    .subject(userDetails.getId())
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                    .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .compact();
            return new ResponseMessage(token);
        } catch (LoginException e) {
            throw e;
        } catch (Exception e) {
            throw new LoginException("Error Occurred while " +
                    "Authorizing the user with email : " + loginDto.getEmail(), e);
        }
    }
}
