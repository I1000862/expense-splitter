package com.example.expensesplitter.dto.request.group;

import com.example.expensesplitter.enums.group.Currency;
import com.example.expensesplitter.validation.ValidEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateGroupRequestDto {
    @NotEmpty(message = "Group name cannot be null")
    @Size(min = 5, message = "String length should be more than 5")
    private String name;

    @ValidEnum(enumClass = Currency.class, message = "Invalid currency value")
    private String currency;
    private String photoUrl;
    private String type;
}
