package com.example.expensesplitter.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequestDto {
    private String username;
    private String email;
}