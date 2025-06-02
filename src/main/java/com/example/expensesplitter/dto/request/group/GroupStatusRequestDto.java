package com.example.expensesplitter.dto.request.group;

import com.example.expensesplitter.enums.group.GroupStatus;
import com.example.expensesplitter.validation.ValidEnum;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupStatusRequestDto {
    @NotEmpty(message = "Status value is required.")
    @ValidEnum(enumClass = GroupStatus.class, message = "Status must be either 'active' or 'inactive'.")
    private String status;
}
