This project was built as an exercise to practice Code quality, architecture choices and testing practices.
It is a bank account application implemented using Hexagonal Architecture (Ports & Adapters).

*The application follows Hexagonal Architecture, organized into:
-Domain: Core business logic
-Ports
      Inbound ports (use cases)
      Outbound ports (repositories)
-Adapters
      Inbound: REST controllers
      Outbound: In-memory persistence
This structure ensures that the domain remains independent of frameworks and technical concerns.

*Features:
   -Bank Account 
Unique account number
Balance management
Deposit operation
Withdrawal operation->A withdrawal cannot exceed the available balance.
   -Current Account
Unique account number
Deposit operation
Withdrawal operation
   -Savings Account
Deposit ceiling
No overdraft allowed
   -Account Statement
Account type (Current / Savings)
Current balance
List of operations
Deposits and withdrawals

*The application exposes REST endpoints to:
Create accounts
Deposit money
Withdraw money
Retrieve account statements


*Tech stack:
Java
Spring Boot
Maven
JUnit 5
Mockito