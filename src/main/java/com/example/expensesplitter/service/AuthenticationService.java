package com.example.expensesplitter.service;

import com.example.expensesplitter.dto.request.auth.RegisterUserDto;
import com.example.expensesplitter.dto.response.user.UserResponseDto;

public interface AuthenticationService {
    UserResponseDto register(RegisterUserDto registerUserDto);
}
