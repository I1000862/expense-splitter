package com.example.expensesplitter.dto.response.group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupMemberDto {
    private UUID userId;
    private String username;
    private String email;
    private boolean isOwner;
    private LocalDateTime joinedAt;
}
