package com.example.expensesplitter.entity;

import com.example.expensesplitter.enums.group.Currency;
import com.example.expensesplitter.enums.group.GroupStatus;
import com.example.expensesplitter.enums.group.GroupType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "groups")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private UUID createdBy;

    private String inviteCode;

    private String inviteUrl;

    private String photoUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GroupStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GroupType type;

    @Builder.Default
    @OneToMany(mappedBy = "group", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<GroupMembership> members = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (type == null) {
            type = GroupType.SHARED;
        }

        if (status == null) {
            status = GroupStatus.ACTIVE;
        }
    }

    public void addMember(GroupMembership member) {
        members.add(member);
        member.setGroup(this);
    }

    public boolean isActive() {
        return GroupStatus.ACTIVE.equals(this.getStatus());
    }

    public boolean hasMember(UUID userId) {
        return members.stream()
                      .anyMatch(m -> m.getUser().getId().equals(userId));
    }

    public void removeMember(UUID userId) {
        members.removeIf(m -> m.getUser().getId().equals(userId));
    }
}
