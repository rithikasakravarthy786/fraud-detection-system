package com.project.fraud.repository;

import com.project.fraud.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;



public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    long countByAccountIdAndFraudTrue(Long accountId);

    long countByFraudTrue();

    List<Transaction> findByAccountId(Long accountId);

    List<Transaction> findByAccountIdAndTimestampAfter(Long accountId, LocalDateTime time);

    List<Transaction> findByAccountIdAndTimestampBetween(Long accountId, LocalDateTime start, LocalDateTime end);
}
