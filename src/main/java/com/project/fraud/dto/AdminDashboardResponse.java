
package com.project.fraud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminDashboardResponse {

    private long totalUsers;
    private long totalAccounts;
    private long totalTransactions;
    private long fraudTransactions;
    private long blockedAccounts;
}
