// File: TransactionObserver.java
// DESIGN PATTERN: OBSERVER
// Responsibility: Observer interface to be implemented by all alert services

public interface TransactionObserver {
    void onTransaction(Transaction t);
}
