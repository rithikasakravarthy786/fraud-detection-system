package com.project.fraud.controller;

import com.project.fraud.entity.Account;
import com.project.fraud.entity.FraudAlert;
import com.project.fraud.repository.AccountRepository;
import com.project.fraud.repository.FraudAlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.project.fraud.dto.AdminDashboardResponse;
import com.project.fraud.repository.TransactionRepository;
import com.project.fraud.repository.UserRepository;


import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final FraudAlertRepository fraudRepo;
    private final AccountRepository accountRepo;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @GetMapping("/dashboard")
    public AdminDashboardResponse getDashboard() {

        long totalUsers = userRepository.count();
        long totalAccounts = accountRepo.count();
        long totalTransactions = transactionRepository.count();
        long fraudTransactions = transactionRepository.countByFraudTrue();
        long blockedAccounts = accountRepo.countByBlockedTrue();

        return new AdminDashboardResponse(
                totalUsers,
                totalAccounts,
                totalTransactions,
                fraudTransactions,
                blockedAccounts
        );
    }


    @GetMapping("/frauds")
    public List<FraudAlert> getAllFrauds() {
        return fraudRepo.findAll();
    }

    @PutMapping("/block/{accNo}")
    public String blockAccount(@PathVariable String accNo) {
        Account acc = accountRepo.findByAccountNumber(accNo).orElseThrow();
        acc.setBlocked(true);
        accountRepo.save(acc);
        return "Account Blocked Successfully";
    }
    @PutMapping("/unblock/{accountNumber}")
    public String unblockAccount(@PathVariable String accountNumber) {

        Account acc = accountRepo.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        acc.setBlocked(false);
        accountRepo.save(acc);

        return "Account unblocked successfully";
    }

}
