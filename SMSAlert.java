// File: SMSAlert.java
// DESIGN PATTERN: OBSERVER
// Responsibility: Sends an SMS when a transaction occurs

// ╔══════════════════════════════════╗
// ║   DESIGN PATTERN: OBSERVER       ║
// ╚══════════════════════════════════╝
// Decouples the alerting side-effects from the core transaction logic.
// The Observer pattern allows this SMS service to dynamically subscribe to and be notified 
// about Transaction changes without the Transaction needing to know anything about SMS specifics.

public class SMSAlert implements TransactionObserver {
    @Override
    public void onTransaction(Transaction t) {
        if (t.getStatus() == TransactionStatus.SUCCESS || t.getStatus() == TransactionStatus.FLAGGED) {
            String accId = t.getFromAccount() != null ? t.getFromAccount().getAccountId() : t.getToAccount().getAccountId();
            String phone = t.getFromAccount() != null ? t.getFromAccount().getCustomer().getPhone() : t.getToAccount().getCustomer().getPhone();
            String action = t.getType() == TransactionType.DEPOSIT ? "credited" : "debited";

            if (t.getType() == TransactionType.TRANSFER && t.getFromAccount() != null) {
                action = "debited";
            }
            // Simple generic representation
            System.out.printf("[SMSAlert] SMS to %s: Your account %s has been %s %s%n", 
                              phone, accId, action, BankUtils.formatCurrency(t.getAmount()));
        }
    }
}
