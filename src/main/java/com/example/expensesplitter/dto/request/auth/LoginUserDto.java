package com.example.expensesplitter.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginUserDto {
    @NotEmpty(message = "Email is required.")
    @Email(message = "Invalid email.")
    private String email;

    @NotEmpty(message = "Password is required.")
    private String password;
}
