package com.example.expensesplitter.dto.response.success;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SuccessResponseDto<T> {
    private String message;

    private T data;
}

