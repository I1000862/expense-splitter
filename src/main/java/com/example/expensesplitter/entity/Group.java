package com.example.expensesplitter.entity;

import com.example.expensesplitter.enums.Currency;
import com.example.expensesplitter.enums.GroupStatus;
import com.example.expensesplitter.enums.GroupType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
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

    private String name;

//    will add this later after auth
//    private UUID createdBy;

    private String inviteCode;

    private String inviteUrl;

    private String photoUrl;

    @Enumerated(EnumType.STRING)
    private GroupStatus status;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    private GroupType type;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
