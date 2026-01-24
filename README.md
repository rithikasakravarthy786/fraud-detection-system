# Fraud Detection & Monitoring System

## About the Project
This is a backend application developed using Spring Boot to simulate a simple fraud detection system for financial transactions.  
The main goal of this project is to identify suspicious transactions using rule-based logic and allow administrators to monitor and control fraudulent activities.

This project helped me gain hands-on experience in backend development, REST API design, database integration, and real-world problem solving using Java and Spring Boot.

---

## Technologies Used
- Java
- Spring Boot
- Spring Data JPA
- REST APIs
- MySQL
- Maven
- Postman

---

## Key Features
- User registration and login
- Account creation for users
- Deposit, withdraw, and transfer transactions
- Automatic fraud detection for high-value transactions
- Fraud alert generation and storage
- Admin module to view fraud alerts
- Ability to block suspicious accounts

---

## Fraud Detection Logic
A transaction is marked as fraudulent when:
- The transaction amount exceeds a predefined threshold (example: greater than 50,000)

When fraud is detected:
- The transaction is flagged
- A fraud alert is generated and stored in the database
- Admin can review the alert and take action

---

## Database Design
The application uses the following tables:
- users
- account
- transaction
- fraud_alert

---

## API Endpoints

### Authentication APIs
- POST `/auth/register`
- POST `/auth/login`

### Account APIs
- POST `/account/create/{userId}`

### Transaction APIs
- POST `/transaction/deposit`
- POST `/transaction/withdraw`
- POST `/transaction/transfer`

### Admin APIs
- GET `/admin/frauds`
- PUT `/admin/block/{accountNumber}`

---

## How to Run the Project
1. Clone the repository from GitHub
2. Create a MySQL database (example: `fraud_db`)
3. Update database credentials in `application.properties`
4. Run the application using an IDE or `mvn spring-boot:run`
5. Test the APIs using Postman

---

## Learning Outcome
Through this project, I learned:
- How to build RESTful APIs using Spring Boot
- How to design layered architecture
- How to use JPA and Hibernate
- How fraud detection works in real-world systems
- How to debug backend issues



