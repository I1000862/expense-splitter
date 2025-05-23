package com.example.expensesplitter.service;

import com.example.expensesplitter.dto.request.UserRequestDto;
import com.example.expensesplitter.dto.response.user.UserResponseDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserResponseDto> getAllUsers();

    UserResponseDto getUserById(UUID id);

    UserResponseDto updateUser(UUID id, UserRequestDto userRequestDto);
}
