package com.example.expensesplitter.dto.response.group;

import com.example.expensesplitter.enums.group.GroupStatus;
import com.example.expensesplitter.enums.group.GroupType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupResponseDto {
    private String name;
    private String inviteCode;
    private String inviteUrl;
    private String photoUrl;
    private GroupStatus status;
    private String currencyCode;
    private String currencySymbol;
    private GroupType type;
    private LocalDateTime createdAt;

    private List<GroupMemberDto> members;
}
