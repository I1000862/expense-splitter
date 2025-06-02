package com.example.expensesplitter.service;

import com.example.expensesplitter.dto.request.auth.LoginUserDto;
import com.example.expensesplitter.dto.request.auth.RefreshTokenDto;
import com.example.expensesplitter.dto.request.auth.RegisterUserDto;
import com.example.expensesplitter.dto.response.auth.LoginResponseDto;
import com.example.expensesplitter.dto.response.user.UserResponseDto;

public interface AuthenticationService {
    UserResponseDto register(RegisterUserDto registerUserDto);

    LoginResponseDto login(LoginUserDto loginUserDto);

    LoginResponseDto refreshToken(RefreshTokenDto refreshToken);
}
