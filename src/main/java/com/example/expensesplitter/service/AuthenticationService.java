package com.example.expensesplitter.service;

import com.example.expensesplitter.dto.request.auth.LoginUserDto;
import com.example.expensesplitter.dto.request.auth.RegisterUserDto;
import com.example.expensesplitter.dto.response.user.UserResponseDto;
import com.example.expensesplitter.entity.User;

public interface AuthenticationService {
    UserResponseDto register(RegisterUserDto registerUserDto);

    User authenticate(LoginUserDto loginUserDto);
}
