package com.example.expensesplitter.service.impl;

import com.example.expensesplitter.dto.request.auth.LoginUserDto;
import com.example.expensesplitter.dto.request.auth.RegisterUserDto;
import com.example.expensesplitter.dto.response.auth.LoginResponseDto;
import com.example.expensesplitter.dto.response.user.UserResponseDto;
import com.example.expensesplitter.entity.User;
import com.example.expensesplitter.exception.EmailAlreadyInUseException;
import com.example.expensesplitter.exception.ResourceNotFoundException;
import com.example.expensesplitter.repository.UserRepository;
import com.example.expensesplitter.security.jwt.JwtService;
import com.example.expensesplitter.service.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    public AuthenticationServiceImpl(
            UserRepository userRepository, PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager, JwtService jwtService
                                    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
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

    @Override
    public LoginResponseDto login(LoginUserDto loginUserDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserDto.getEmail(), loginUserDto.getPassword()
                ));

        User user = userRepository.findByEmail(loginUserDto.getEmail())
                                  .orElseThrow(() -> new ResourceNotFoundException(
                                          "No user with this email"));

        String accessToken = jwtService.generateToken(user, false);
        String refreshToken = jwtService.generateToken(user, true);

        return LoginResponseDto.builder()
                               .accessToken(accessToken)
                               .refreshToken(refreshToken)
                               .expiresIn(jwtService.getExpirationTime(false))
                               .refreshExpiresIn(jwtService.getExpirationTime(true))
                               .build();
    }

}
