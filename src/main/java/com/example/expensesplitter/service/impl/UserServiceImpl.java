package com.example.expensesplitter.service.impl;

import com.example.expensesplitter.dto.request.user.UserRequestDto;
import com.example.expensesplitter.dto.response.user.UserResponseDto;
import com.example.expensesplitter.entity.User;
import com.example.expensesplitter.repository.UserRepository;
import com.example.expensesplitter.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                             .stream()
                             .map(this::convertToDto)
                             .collect(Collectors.toList());
    }

    @Override
    public UserResponseDto getUserById(UUID id) {
        User user = userRepository.findById(id)
                                  .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return convertToDto(user);
    }

    @Override
    public UserResponseDto updateUser(UUID id, UserRequestDto userRequestDto) {
        User user = userRepository.findById(id)
                                  .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        user.setUsername(userRequestDto.getUsername());
        user.setEmail(userRequestDto.getEmail());
        return convertToDto(userRepository.save(user));
    }

    private UserResponseDto convertToDto(User user) {
        return UserResponseDto.builder()
                              .username(user.getUsername())
                              .email(user.getEmail())
                              .profilePictureUrl(user.getProfilePictureUrl())
                              .build();
    }
}
