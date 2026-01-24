Hi, I am working on a Spring Boot Fraud Detection & Monitoring System project.

Tech stack:
- Java, Spring Boot, Spring Data JPA
- MySQL, Maven, REST APIs
- Postman

Project features already implemented:

1) Core Entities:
- User
- Account
- Transaction (with riskScore field)
- FraudAlert

2) APIs:
- Auth APIs: register, login
- Transaction APIs: deposit, withdraw, transfer
- Admin APIs: view frauds, block account, unblock account, dashboard

3) Fraud Detection System:
- Multi-rule fraud detection:
  - High amount transaction (>50000)
  - Multiple transactions within 1 minute
  - Daily transaction limit exceeded (>100000)
- FraudResult class used to calculate:
  - fraud flag (true/false)
  - reason
  - riskScore

4) Risk Score System:
- riskScore stored in DB (transaction table)
- riskScore calculated using fraud rules
- riskScore column added in MySQL
- Transaction entity has:
  @Column(name="risk_score")
  private int riskScore;

5) Auto Account Block Feature:
- If fraud transactions >= 3 for an account → account auto blocked
- Logic implemented in TransactionService
- Blocked accounts cannot perform transactions

6) Admin Unblock Feature:
- API: PUT /admin/unblock/{accountNumber}
- Admin can manually unblock blocked accounts

7) Admin Dashboard API:
- API: GET /admin/dashboard
- Returns:
  - totalUsers
  - totalAccounts
  - totalTransactions
  - fraudTransactions
  - blockedAccounts

8) Global Exception Handling:
- GlobalExceptionHandler class created
- Handles AccountBlockedException and general exceptions
- Avoids 500 Internal Server Error
- Returns proper error messages

9) TransactionService:
- saveTransaction() method includes:
  - save transaction
  - calculate fraud rules
  - set riskScore
  - set fraud flag
  - create FraudAlert
  - auto block account
  - save final transaction

10) TransactionRepository methods:
- countByAccountIdAndFraudTrue(Long accountId)
- countByFraudTrue()
- findByAccountIdAndTimestampAfter(...)
- findByAccountIdAndTimestampBetween(...)

11) AccountRepository methods:
- findByAccountNumber(String accountNumber)
- countByBlockedTrue()

12) Current status:
- riskScore working correctly
- auto block working
- admin unblock working
- dashboard working
- proper error messages working

I want to continue improving this fraud detection system with more advanced features, clean architecture, and professional-level enhancements.
