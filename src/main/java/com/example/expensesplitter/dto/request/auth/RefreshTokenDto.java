package com.example.expensesplitter.dto.request.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RefreshTokenDto {
    @NotEmpty(message = "Refresh token is required.")
    public String refreshToken;
}
