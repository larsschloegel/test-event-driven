package com.example.eventdriven.repo.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "user_rest",
        indexes = {
                @Index(name = "idx_email_unique",
                        columnList = "email",
                        unique = true)
        })
public class UserRest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;
    private String payload;
}
