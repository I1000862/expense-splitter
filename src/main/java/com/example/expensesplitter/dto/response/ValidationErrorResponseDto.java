package com.example.expensesplitter.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValidationErrorResponseDto {
    private String error;

    private String message;

    private List<ValidationErrorDetail> details;
}
