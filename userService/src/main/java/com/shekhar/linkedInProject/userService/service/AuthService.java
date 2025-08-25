package com.shekhar.linkedInProject.userService.service;

import com.shekhar.linkedInProject.userService.dto.LoginRequestDto;
import com.shekhar.linkedInProject.userService.dto.SignupRequestDto;
import com.shekhar.linkedInProject.userService.dto.UserDto;
import com.shekhar.linkedInProject.userService.entiy.User;
import com.shekhar.linkedInProject.userService.exception.BadRequestException;
import com.shekhar.linkedInProject.userService.repository.UserRepository;
import com.shekhar.linkedInProject.userService.utils.BCrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;

    public UserDto signup(SignupRequestDto signupRequestDto) {
        log.info("Signup user with email:{}", signupRequestDto.getEmail());

        boolean exists = userRepository.existsByEmail(signupRequestDto.getEmail());
        if (exists) {
            throw new BadRequestException("user is already exists");
        }
        User user = modelMapper.map(signupRequestDto, User.class);

        user.setPassword(BCrypt.hash(signupRequestDto.getPassword()));

        user = userRepository.save(user);

        return modelMapper.map(user,UserDto.class);

    }

    public String login(LoginRequestDto loginRequestDto) {
        log.info("Login user with email:{}", loginRequestDto.getEmail());
        User user = userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(()->
                new BadRequestException("Incorrect email and Password"));
        boolean isPasswordMatch = BCrypt.match(loginRequestDto.getPassword(),user.getPassword());
        if (!isPasswordMatch) {
            throw new BadRequestException("Incorrect email and Password");
        }
     return jwtService.generateAccessToken(user);
    }
}
