package com.project.fraud.service;

import com.project.fraud.entity.*;
import com.project.fraud.repository.*;
import com.project.fraud.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.project.fraud.dto.FraudResult;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final FraudAlertRepository fraudAlertRepository;

    public Transaction deposit(String accNo, double amount) {
        Account acc = getAccount(accNo);
        if (acc.isBlocked()) throw new AccountBlockedException("Account is blocked");

        acc.setBalance(acc.getBalance() + amount);
        accountRepository.save(acc);

        return saveTransaction(acc, "DEPOSIT", amount);
    }

    public Transaction withdraw(String accNo, double amount) {
        Account acc = getAccount(accNo);

        if (acc.isBlocked()) throw new AccountBlockedException("Account Blocked");
        if (acc.getBalance() < amount) throw new InsufficientBalanceException("Not enough balance");

        acc.setBalance(acc.getBalance() - amount);
        accountRepository.save(acc);

        return saveTransaction(acc, "WITHDRAW", amount);
    }

    public Transaction transfer(String fromAcc, String toAcc, double amount) {

        Account sender = getAccount(fromAcc);
        Account receiver = getAccount(toAcc);

        if (sender.isBlocked()) throw new AccountBlockedException("Sender account blocked");
        if (sender.getBalance() < amount) throw new InsufficientBalanceException("Insufficient balance");

        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        accountRepository.save(sender);
        accountRepository.save(receiver);

        return saveTransaction(sender, "TRANSFER", amount);
    }

    // ================= SAVE TRANSACTION + FRAUD LOGIC =================
    private Transaction saveTransaction(Account acc, String type, double amount) {

        Transaction tx = Transaction.builder()
                .account(acc)
                .type(type)
                .amount(amount)
                .timestamp(LocalDateTime.now())
                .fraud(false)
                .riskScore(0)
                .build();

        // ✅ FIRST save transaction
        Transaction savedTx = transactionRepository.save(tx);

        // ✅ FRAUD RULE ENGINE
        FraudResult fraudResult = checkFraudRules(savedTx);

        System.out.println("🔥 Risk Score = " + fraudResult.getRiskScore()); // DEBUG LINE

        // ✅ SET risk score ALWAYS
        savedTx.setRiskScore(fraudResult.getRiskScore());

        // ✅ SET fraud based on risk score
        if (fraudResult.isFraud()) {
            savedTx.setFraud(true);

            FraudAlert fraudAlert = FraudAlert.builder()
                    .reason(fraudResult.getReason())
                    .detectedAt(LocalDateTime.now())
                    .transaction(savedTx)
                    .build();

            fraudAlertRepository.save(fraudAlert);

            // 🚨 AUTO BLOCK ACCOUNT
            long fraudCount = transactionRepository.countByAccountIdAndFraudTrue(acc.getId());

            System.out.println("Fraud Count = " + fraudCount);

            if (fraudCount >= 3 && !acc.isBlocked()) {
                acc.setBlocked(true);
                accountRepository.save(acc);
            }

        }

        // ✅ SAVE FINAL UPDATED TRANSACTION
        savedTx = transactionRepository.save(savedTx);

        return savedTx;
    }







    // ================= MULTI-RULE FRAUD ENGINE =================

    private FraudResult checkFraudRules(Transaction tx) {

        Long accountId = tx.getAccount().getId();
        double amount = tx.getAmount();
        LocalDateTime now = LocalDateTime.now();

        int riskScore = 0;
        StringBuilder reason = new StringBuilder();

        // RULE 1: High Amount Transaction
        if (amount > 50000) {
            riskScore += 50;
            reason.append("High amount transaction; ");
        }

        // RULE 2: Multiple transactions within 1 minute
        LocalDateTime oneMinuteAgo = now.minusMinutes(1);
        List<Transaction> recentTxns =
                transactionRepository.findByAccountIdAndTimestampAfter(accountId, oneMinuteAgo);

        if (recentTxns.size() >= 3) {
            riskScore += 30;
            reason.append("Multiple transactions in short time; ");
        }

        // RULE 3: Daily transaction limit exceeded (1,00,000)
        LocalDateTime startOfDay = now.toLocalDate().atStartOfDay();
        List<Transaction> todayTxns =
                transactionRepository.findByAccountIdAndTimestampBetween(accountId, startOfDay, now);

        double todayTotal = todayTxns.stream().mapToDouble(Transaction::getAmount).sum();

        if (todayTotal + amount > 100000) {
            riskScore += 40;
            reason.append("Daily limit exceeded; ");
        }

        // FINAL DECISION
        if (riskScore >= 70) {
            return new FraudResult(true, reason.toString(), riskScore);
        }

        return new FraudResult(false, "No fraud detected", riskScore);
    }

    private Account getAccount(String accNo) {
        return accountRepository.findByAccountNumber(accNo)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
    }
}
