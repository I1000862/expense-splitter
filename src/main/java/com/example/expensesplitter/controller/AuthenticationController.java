package com.example.expensesplitter.controller;

import com.example.expensesplitter.dto.request.auth.LoginUserDto;
import com.example.expensesplitter.dto.request.auth.RegisterUserDto;
import com.example.expensesplitter.dto.response.auth.LoginResponseDto;
import com.example.expensesplitter.dto.response.success.SuccessResponseDto;
import com.example.expensesplitter.dto.response.user.UserResponseDto;
import com.example.expensesplitter.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;


    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<SuccessResponseDto> register(@Valid @RequestBody RegisterUserDto registerUserDto) {
        UserResponseDto registeredUser = authenticationService.register(registerUserDto);

        return ResponseEntity.ok(SuccessResponseDto.<UserResponseDto>builder()
                                                   .message("User registered successfully.")
                                                   .data(registeredUser)
                                                   .build());
    }

    @PostMapping("/login")
    public ResponseEntity<SuccessResponseDto> login(@Valid @RequestBody LoginUserDto loginUserDto) {
        LoginResponseDto loginResponse = authenticationService.login(loginUserDto);

        return ResponseEntity.ok(SuccessResponseDto.<LoginResponseDto>builder()
                                                   .message("User logged in.")
                                                   .data(loginResponse)
                                                   .build());
    }


}
