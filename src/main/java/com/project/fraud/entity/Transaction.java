package com.project.fraud.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;

    private String type;
    private double amount;
    private LocalDateTime timestamp;
    private boolean fraud = false;
    @Column(name = "risk_score")
    private int riskScore;

    @ManyToOne
    private Account account;


}
