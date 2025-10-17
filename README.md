# Banking System with Transaction Logs

### Proponent(s)
* Clarence Joel Torres - BSCS
* Humphrey Tabanao - BSCS
* Kian Magallanes - BSCS
* Rizza Paga - BSCS
* Sam Vincent Joey Ortega - BSCS
* Vance Kerr B. Amor - BSCS

### Project Overview
This Banking System provides secure digital banking services with intelligent fraud detection. It enables users to perform essential banking operations while maintaining comprehensive transaction records. The system features enhanced fraud detection with scoring accuracy and administrative controls for user management. Developed using Java and JavaFX, it serves customers and administrators with reliable financial transaction processing and security monitoring.

### Features
* User authentication and account management
* Deposit, withdrawal, and fund transfer operations
* Enhanced transaction logging with unique reference numbers
* Comprehensive transaction logging with reference numbers
* Enhanced fraud detection with scoring accuracy
* Administrative panel for user blocking/unblocking
* Real-time balance updates and transaction history
* Performance monitoring of system operations


# Screenshots
### User authentication interface
![login-main-page.png](screenshots%2Flogin-main-page.png)
![Login - Login Page.png](screenshots%2FLogin%20-%20Login%20Page.png)
![Login - Account Creation.png](screenshots%2FLogin%20-%20Account%20Creation.png)

### Main banking transaction interface
![Main - Main Page.png](screenshots%2FMain%20-%20Main%20Page.png)
![Main - Deposit Page.png](screenshots%2FMain%20-%20Deposit%20Page.png)
![Main - Withdraw Page.png](screenshots%2FMain%20-%20Withdraw%20Page.png)
![Main - Bank Transfer Page.png](screenshots%2FMain%20-%20Bank%20Transfer%20Page.png)
![Main - Transaction History Page.png](screenshots%2FMain%20-%20Transaction%20History%20Page.png)

### Administrative panel with fraud detection
![Admin Panel - Main Page.png](screenshots%2FAdmin%20Panel%20-%20Main%20Page.png)
![Admin Panel - Running Fraud Checker.png](screenshots%2FAdmin%20Panel%20-%20Running%20Fraud%20Checker.png)


# How to Run the Program
* Ensure Java JDK 8 or higher is installed on your system
* Open the project in any Java IDE (IntelliJ IDEA, Eclipse, or NetBeans)
* Configure JavaFX in your IDE settings
* Navigate to the main class: sys/main/Main.java
* Run the main class to launch the application

### Default Admin Credentials:

Username: admin - 
Password: 123

# Revisions Based on Panel Feedback
### Major Improvements Implemented:
1. Enhanced Fraud Detection Accuracy - Implemented scoring system for fraud detection with quantifiable metrics. Enhanced algorithm to analyze individual user transaction patterns. Added personalized fraud assessment based on user behavior. Improved detection accuracy through refined scoring thresholds.

2. Improved Fraud Detection Algorithm - Refined Isolation Forest implementation with better feature analysis. Added individual user baseline calculation for personalized detection. Implemented multiple fraud indicators and scoring mechanisms. Enhanced algorithm to provide more accurate fraud identification.

3. Enhanced Transaction Tracking - Implemented unique reference number generation for all transactions. Added comprehensive transaction logging with traceable reference codes. Improved audit trail capabilities with formatted reference numbers (REF-YYYYMMDD-XXXXXX).

4. Self-Initiated System Enhancement - Migrated from file-based storage to SQLite database for improved reliability. Created structured database schema for users and transactions. Added efficient data management with JDBC connectivity. Enhanced system performance and data integrity.