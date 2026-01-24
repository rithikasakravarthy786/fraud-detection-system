package com.project.fraud.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    private String accountNumber;

    private double balance;

    private boolean blocked = false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
