package com.example.expensesplitter.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users" )
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "username", nullable = false)
    private String username;


    @Column(name = "email", unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String profilePictureUrl;
}
