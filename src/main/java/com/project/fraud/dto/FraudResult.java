package com.project.fraud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FraudResult {
    private boolean fraud;
    private String reason;
    private int riskScore;
}
