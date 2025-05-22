package com.example.expensesplitter.dto.request.group;

import com.example.expensesplitter.enums.Currency;
import com.example.expensesplitter.validation.ValidEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupRequestDto {
    @NotNull(message = "Group name cannot be null")
    @Size(min = 5, message = "String length should be more than 5")
    private String name;

    @NotNull(message = "Currency is required")
    @NotBlank(message = "Currency cannot be null")
    @ValidEnum(enumClass = Currency.class, message = "Invalid currency value")
    private String currency;
    private String photoUrl;
    private String type;
}
