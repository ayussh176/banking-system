// File: Transaction.java
// DESIGN PATTERN: OBSERVER
// Responsibility: Transaction execution and Subject for observers

// ╔══════════════════════════════════╗
// ║   DESIGN PATTERN: OBSERVER       ║
// ╚══════════════════════════════════╝
// Transaction is the Subject. It maintains state and manages observers list.
// By doing this, it isolates the core transaction execution from any downstream 
// effects (SMS, Email, Fraud checks).

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Transaction {
    private String transactionId;
    private Account fromAccount;
    private Account toAccount;
    private double amount;
    private TransactionType type;
    private TransactionStatus status;
    private LocalDateTime timestamp;

    private List<TransactionObserver> observers = new ArrayList<>();

    public Transaction(Account fromAccount, Account toAccount, double amount, TransactionType type) {
        this.transactionId = BankUtils.generateId();
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.type = type;
        this.status = TransactionStatus.FAILED; // default until executed
        this.timestamp = LocalDateTime.now();
    }

    public void addObserver(TransactionObserver o) {
        observers.add(o);
    }

    public void removeObserver(TransactionObserver o) {
        observers.remove(o);
    }

    private void notifyObservers() {
        for (TransactionObserver observer : observers) {
            observer.onTransaction(this);
        }
    }

    public void execute() {
        boolean success = false;
        
        switch (type) {
            case DEPOSIT:
                if (toAccount != null) {
                    toAccount.deposit(amount);
                    success = true;
                }
                break;
            case WITHDRAWAL:
                if (fromAccount != null) {
                    success = fromAccount.withdraw(amount);
                }
                break;
            case TRANSFER:
                if (fromAccount != null && toAccount != null) {
                    if (fromAccount.withdraw(amount)) {
                        toAccount.deposit(amount);
                        success = true;
                    }
                }
                break;
            case INTEREST:
                if (toAccount != null && toAccount instanceof SavingsAccount) {
                    ((SavingsAccount) toAccount).applyMonthlyInterest();
                    success = true;
                }
                break;
        }

        if (success) {
            this.status = TransactionStatus.SUCCESS;
        } else {
            this.status = TransactionStatus.FAILED;
            System.out.printf("Transaction %s failed! Amount: %s%n", transactionId, BankUtils.formatCurrency(amount));
        }

        // Notify all registered observers after state change
        notifyObservers();
    }

    public String getTransactionId() { return transactionId; }
    public Account getFromAccount() { return fromAccount; }
    public Account getToAccount() { return toAccount; }
    public double getAmount() { return amount; }
    public TransactionType getType() { return type; }
    public TransactionStatus getStatus() { return status; }
    public LocalDateTime getTimestamp() { return timestamp; }
    
    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
}
