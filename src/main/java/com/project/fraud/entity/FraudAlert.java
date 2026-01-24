package com.project.fraud.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FraudAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fraud_alertid")
    private Long id;

    private String reason;
    private LocalDateTime detectedAt;

    @OneToOne
    @JsonIgnore
    private Transaction transaction;

}
