package com.example.expensesplitter.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupMembership {
    @EmbeddedId
    @Builder.Default
    private GroupMembershipId id = new GroupMembershipId();

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    private Group group;

    private Boolean isOwner;

    @CreationTimestamp
    private LocalDateTime joinedAt;
}
