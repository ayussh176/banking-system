// File: FraudDetectionService.java
// DESIGN PATTERN: OBSERVER
// Responsibility: Flags transactions above Rs. 50,000

// ╔══════════════════════════════════╗
// ║   DESIGN PATTERN: OBSERVER       ║
// ╚══════════════════════════════════╝
// This observer acts as an interceptor/validator. By separating it from Transaction, 
// we keep fraud detection rules decoupled and easily testable/swappable in the future.

public class FraudDetectionService implements TransactionObserver {
    @Override
    public void onTransaction(Transaction t) {
        if (t.getAmount() > 50000.0) {
            t.setStatus(TransactionStatus.FLAGGED);
            System.out.printf("[FraudDetectionService] WARNING: Large transaction flagged! Amount: %s%n", 
                              BankUtils.formatCurrency(t.getAmount()));
        }
    }
}
