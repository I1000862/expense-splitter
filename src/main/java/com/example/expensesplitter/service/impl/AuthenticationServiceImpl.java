package com.example.expensesplitter.service.impl;

import com.example.expensesplitter.dto.request.auth.RegisterUserDto;
import com.example.expensesplitter.dto.response.user.UserResponseDto;
import com.example.expensesplitter.entity.User;
import com.example.expensesplitter.exception.EmailAlreadyInUseException;
import com.example.expensesplitter.repository.UserRepository;
import com.example.expensesplitter.service.AuthenticationService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponseDto register(RegisterUserDto registerUserDto) {
        if (userRepository.findByEmail(registerUserDto.getEmail()).isPresent()) {
            throw new EmailAlreadyInUseException("Email is already in use.");
        }

        User user = User.builder()
                        .username(registerUserDto.getUsername())
                        .email(registerUserDto.getEmail())
                        .password(passwordEncoder.encode(registerUserDto.getPassword()))
                        .build();
        User registeredUser = userRepository.save(user);

        return UserResponseDto.builder()
                              .email(registeredUser.getEmail())
                              .username(registeredUser.getUsername())
                              .profilePictureUrl(registeredUser.getProfilePictureUrl())
                              .build();
    }

}
