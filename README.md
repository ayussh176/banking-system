# 🏦 SmartBank — Banking Management System

SmartBank is a robust, entirely console-based Java application simulating a modernized banking environment. It has been strictly designed using **pure standard Java** (no `pom.xml`, no Spring Boot, no external frameworks—only `java.util.*` and Java standard libraries) prioritizing deep architectural structure.

The central intent of this project is to explicitly demonstrate Software Engineering structural excellence through the rigorous, practical application of three core **Gang of Four (GoF) Design Patterns**: 
1. **Singleton**
2. **Factory**
3. **Observer**

---

## 🏗️ Architectural Core: The Design Patterns

### 1. Singleton Pattern
**Location:** `BankDatabase.java`
**Definition:** Restricts the instantiation of a class to one "single" instance.
**Implementation & Rationale:**
In a real-world application, a database connection pool or an in-memory cache must not be duplicated. Multiple instances of the database would cause data desynchronization (e.g., one database says an account has $500, another says it has $0). 
In SmartBank, the constructor is strictly `private`. Variables are held locally in a `private static BankDatabase instance` map. The system retrieves it strictly via `BankDatabase.getInstance()`. This enforces a unified point of truth for all mapped `Account` entries and `Transaction` ledgers.

### 2. Factory Pattern
**Location:** `AccountFactory.java`
**Definition:** A creational pattern that abstracts object instantiation logic to a creator class.
**Implementation & Rationale:**
When creating banking profiles, the `Main.java` client shouldn't be burdened with understanding the distinct constructor requirements of `SavingsAccount`, `CurrentAccount`, and `FixedDepositAccount`. 
`AccountFactory.createAccount(type, ...)` acts as the delegator. It accepts a string identifier (`"savings"`, `"current"`, `"fixed"`) and handles the varying concrete allocations internally. This creates a scalable system (e.g., if we want to add a `"corporate"` account tomorrow, we only update the Factory, while the rest of the application remains completely unaware of internal changes).

### 3. Observer Pattern
**Location:** `Transaction.java` (Subject), `TransactionObserver.java` & (`SMSAlert`, `EmailAlert`, `FraudDetectionService`) (Observers).
**Definition:** A behavioral pattern where an object (Subject) maintains a list of its dependents (Observers) and automatically notifies them of any state changes.
**Implementation & Rationale:**
Execution logic should be tightly encapsulated. A `Transaction` should only care about moving money. It should *not* care about how to send text messages or validate compliance laws. 
When a transaction is fired via `execute()`, it mutates the state, sets a `TransactionStatus`, and calls `notifyObservers()`. The dynamically subscribed listener classes (`SMSAlert`, `EmailAlert`, `FraudDetectionService`) catch this completion signal and respond independently. This demonstrates highly decoupled, event-driven architecture. 

---

## 🗂️ Complete Directory & Class Reference Directory

The architecture spans 16 individual `.java` files encapsulating strict Single-Responsibility limits.

### ⚙️ Core Configuration & Utilities
* **`Main.java`**: The global execution driver orchestrating the demonstrations.
* **`BankUtils.java`**: Formatter helper grouping generic functionalities like generating UUIDs, rendering the `Rs. X,XXX.XX` currency format, and generating clean console UI dividers.
* **`Customer.java`**: Basic POJO linking generated ID strings to demographic details (Name, Email, Phone).

### 🗄️ State & Storage
* **`BankDatabase.java`**: The Singleton managing `Map<String, Account> accounts` and `List<Transaction> transactions`.

### 💰 Account Abstract Layers & Concretes
* **`AccountFactory.java`**: Centralized Factory generator producing accounts avoiding direct calls.
* **`Account.java`**: The abstract model guaranteeing standardization of `accountId`, `customer`, `balance`, alongside polymorphic `deposit()` and `withdraw()` templates.
* **`SavingsAccount.java`**: Concrete class. Enforces limits (`withdrawalsThisMonth >= monthlyWithdrawalLimit`) alongside custom `applyMonthlyInterest()` methods.
* **`CurrentAccount.java`**: Concrete class. Introduces an operational negative margin through static fields representing overdraft limit capabilities.
* **`FixedDepositAccount.java`**: Concrete class. Locks asset transfers leveraging `java.time.LocalDate` and penalizes withdrawals before the maturity index.

### 🔄 The Transaction Subject
* **`Transaction.java`**: Instantiates a lifecycle bridging `fromAccount` down to `toAccount`. Defines Subjects and dictates iteration across registered observers. 
* **`TransactionType.java`**: Configuration enumerable mapping `DEPOSIT, WITHDRAWAL, TRANSFER, INTEREST`.
* **`TransactionStatus.java`**: Completion enumerable asserting operations as `SUCCESS, FAILED, FLAGGED`.

### 📡 Event Listeners (The Observers)
* **`TransactionObserver.java`**: Crucial framework interface assigning a rigid `onTransaction(Transaction t)` target for any listening class.
* **`SMSAlert.java`**: Catches successful/flagged events and targets the linked account phone parameter.
* **`EmailAlert.java`**: Extrapolates detailed data sets dynamically forwarding simulated email responses.
* **`FraudDetectionService.java`**: Injects interceptor logic checking parsed integers. Operations exceeding `Rs. 50,000` throw targeted system warning interrupts and retrospectively mutate states to `FLAGGED`.

---

## 💼 Business Rules Implemented

| Account Type | Standard Rules Set |
| :--- | :--- |
| **Savings Account** | Interest Rate set to `4%` annually (applicable monthly via independent triggers). Hard-locks withdrawal frequency to no more than `5 times` a month. |
| **Current Account** | Allows withdrawing down deeply past a zero balance by leaning into a flexible `Rs. 50,000` overdraft grace ceiling. |
| **Fixed Deposit** | Binds initial investments across a strict `1 year constraint`. Withdrawals forced before maturity dates are forcibly truncated by a static `2% punitive penalty margin`. |

---

## 🚀 Setup & Execution Guide

The workflow ensures seamless transitions without necessitating an IDE or an external build compiler plugin.

> **Windows Console Warning:** This codebase heavily specifies visual elements relying on proper UTF-8 Box Drawing mechanics (`// ╔══════════╗`). By default, older Windows terminal encoding tries parsing using the localized `windows-1252` encoding string, resulting in unmappable character build throws. Please specify the target rendering during compilation.

### Step 1: Compilation
Ensure you're inside the root directory where the `.java` files persist. Tell `javac` to explicitly interpret files properly.
```bash
javac -encoding UTF-8 *.java
```
*(If errors persist regarding class file configurations between JDK ranges, define your runtime compilation directly: `javac -source 8 -target 8 -encoding UTF-8 *.java`)*

### Step 2: Execution
Simply trigger the main orchestrator script directly via the Java environment.
```bash
java Main
```

### 📋 Expected Application Console Flow:
When effectively compiled, the script independently runs the full diagnostic sequence spanning the patterns:
1. Validating `BankDatabase.getInstance()` pointer memory addresses prove identical Singleton truths.
2. The runtime constructs three dynamic Customers leveraging exclusively against `AccountFactory`.
3. Newly populated nodes write implicitly into the database maps.
4. Independent observer systems instantiate and stand by.
5. Five varying financial interactions trigger (including one bound to fail correctly, and one large deposit triggering the Fraud detection limits).
6. Centralized tracking parses the final recorded states.
7. Final adjusted metrics represent standard `Rs. X,XXX.XX` configurations indicating flawless mathematical operations globally.
