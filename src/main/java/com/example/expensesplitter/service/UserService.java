package com.example.expensesplitter.service;

import com.example.expensesplitter.dto.request.UserRequestDto;
import com.example.expensesplitter.dto.response.UserResponseDto;
import com.example.expensesplitter.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    List<UserResponseDto> getAllUsers();
    UserResponseDto getUserById(UUID id);
    UserResponseDto createUser(UserRequestDto userRequestDto);
    UserResponseDto updateUser(UUID id, UserRequestDto userRequestDto);
}
