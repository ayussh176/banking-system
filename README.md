# 🏦 SmartBank — Banking Management System

SmartBank is a robust, entirely console-based Java application simulating a modernized banking environment. It has been strictly designed using **pure standard Java** (no `pom.xml`, no Spring Boot, no external frameworks—only `java.util.*` and Java standard libraries) prioritizing deep architectural structure.

The central intent of this project is to explicitly demonstrate Software Engineering excellence by utilizing all three categories of **Gang of Four (GoF) Design Patterns**:

---

## 🏗️ Architectural Core: Design Pattern Categories

### 🟢 1. CREATIONAL PATTERNS
*Dealing with object creation mechanisms to create objects in a manner suitable to the situation.*

#### **Singleton Pattern** (`BankDatabase.java`)
**Definition:** Restricts the instantiation of a class to one "single" instance.
**Rationale:** In a banking context, data consistency is paramount. Having multiple database instances would lead to synchronization errors. The `private` constructor and `static getInstance()` method ensure a single, consistent state for all accounts.

#### **Factory Pattern** (`AccountFactory.java`)
**Definition:** Abstracts object instantiation logic to a creator class.
**Rationale:** Decouples the client (`Main.java`) from the concrete account classes (`SavingsAccount`, etc.). This allows the bank to add new account types (e.g., "Corporate") without modifying the central business logic.

---

### 🔵 2. STRUCTURAL PATTERNS
*Dealing with object composition and providing new ways to compose objects to realize new functionality.*

#### **Decorator Pattern** (`AccountDecorator.java`, `InsuranceDecorator.java`)
**Definition:** Allows behavior to be added to an individual object, dynamically, without affecting the behavior of other objects from the same class.
**Rationale:** In SmartBank, we use this to wrap any `Account` with additional services like **Insurance**. This demonstrates how to structurally compose objects to extend functionality at runtime without bloat or inheritance complexity.

---

### 🟡 3. BEHAVIORAL PATTERNS
*Dealing with communication between objects and how they operate.*

#### **Observer Pattern** (`Transaction.java` as Subject, `SMSAlert.java`, etc. as Observers)
**Definition:** Defines a subscription mechanism to notify multiple objects about any events that happen to the object they're observing.
**Rationale:** Separates core financial logic from side-effects. The `Transaction` class only handles balance changes, while observers handle notifications (SMS, Email) and security audits (Fraud Detection) independently.

---

## 💼 Integrated Business Rules

| Feature | Savings Account | Current Account | Fixed Deposit |
| :--- | :--- | :--- | :--- |
| **Interest Rate** | 4% (Applied Monthly) | N/A | 7% (At Maturity) |
| **Withdrawal Limits** | 5 Transactions / Month | Unlimited (Overdraft) | 1 Year Lock-in |
| **Special Logic** | Penalty on overflow | Rs. 50,000 Overdraft | 2% Early Exit Penalty |
| **Structural Addons** | Can be Insured via Decorator | Can be Insured via Decorator | Can be Insured via Decorator |

---

## 🗂️ Class Reference Glossary

### ⚙️ Core Configuration
* **`Main.java`**: Orchestrates the 8-step demonstration of all design patterns.
* **`BankUtils.java`**: High-level utility for ID generation, currency formatting, and UI styling.
* **`Customer.java`**: Domain model for user profile management.

### 💰 Accounts & Decoration
* **`AccountFactory.java`**: **[CREATIONAL]** Central hub for all account instantiation.
* **`Account.java`**: Base contract for all bank holdings.
- **`AccountDecorator.java`**: **[STRUCTURAL]** Base class enabling dynamic feature expansion.
- **`InsuranceDecorator.java`**: **[STRUCTURAL]** Concrete wrapper adding insurance coverage.

### 🔄 Logic & Notifications
* **`Transaction.java`**: **[BEHAVIORAL]** The Subject that executes moves and notifies observers.
* **`TransactionObserver.java`**: Interface for decoupling event listeners.
* **`SMSAlert.java`**, **`EmailAlert.java`**: Multi-channel notification simulations.
* **`FraudDetectionService.java`**: Security observer that flags transactions > Rs. 50,000.

---

## 🚀 Setup & Execution Guide

### Compilation
The project uses UTF-8 characters for premium console headers. Compile with:
```bash
javac -encoding UTF-8 *.java
```

### Execution
Run the system directly using the JVM:
```bash
java Main
```

---

## 📋 Demonstration Highlights
1. **Singleton Integrity**: Proves `db1 == db2` via memory address hashing.
2. **Polymorphic Factory**: Creates three distinct account types through a single factory interface.
3. **Observer Event Loop**: Executes 5 transactions, triggering real-time SMS, Email, and Fraud alerts.
4. **Structural Decoration**: Enhances a live account with **Insurance**, demonstrating runtime object composition and premium application.
