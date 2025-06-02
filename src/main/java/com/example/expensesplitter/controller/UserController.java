package com.example.expensesplitter.controller;

import com.example.expensesplitter.dto.request.user.UserRequestDto;
import com.example.expensesplitter.dto.response.success.SuccessResponseDto;
import com.example.expensesplitter.dto.response.user.UserResponseDto;
import com.example.expensesplitter.entity.User;
import com.example.expensesplitter.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

//this wil be removed later
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<SuccessResponseDto> getAllUsers() {
        List<UserResponseDto> users = userService.getAllUsers();
        SuccessResponseDto<List<UserResponseDto>> res = SuccessResponseDto.<List<UserResponseDto>>builder()
                                                                          .message("Users retrieved successfully.")
                                                                          .data(users)
                                                                          .build();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponseDto> getUserById(@PathVariable UUID id) {
        UserResponseDto user = userService.getUserById(id);
        return ResponseEntity.ok(SuccessResponseDto.<UserResponseDto>builder()
                                                   .message("user retrieved successfully.")
                                                   .data(user)
                                                   .build()
                                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponseDto> updateUser(
            @PathVariable UUID id,
            @RequestBody UserRequestDto requestDto
                                                        ) {
        UserResponseDto user = userService.updateUser(id, requestDto);
        return ResponseEntity.ok(SuccessResponseDto.<UserResponseDto>builder()
                                                   .message("user updated.")
                                                   .data(user)
                                                   .build());
    }

    @GetMapping("/me")
    public ResponseEntity<?> me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(user);
    }
}
