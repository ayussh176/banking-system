// File: EmailAlert.java
// DESIGN PATTERN: OBSERVER
// Responsibility: Sends an Email when a transaction occurs

// ╔══════════════════════════════════╗
// ║   DESIGN PATTERN: OBSERVER       ║
// ╚══════════════════════════════════╝
// Observers allow open-closed compliance: we added Email alerting just by creating 
// a new Observer class rather than modifying the original Transaction execute method.

public class EmailAlert implements TransactionObserver {
    @Override
    public void onTransaction(Transaction t) {
        String email = "unknown@example.com";
        if (t.getFromAccount() != null) {
            email = t.getFromAccount().getCustomer().getEmail();
        } else if (t.getToAccount() != null) {
            email = t.getToAccount().getCustomer().getEmail();
        }
        
        System.out.printf("[EmailAlert] Email to %s: Transaction %s | Amount: %s | Status: %s%n", 
                          email, t.getTransactionId(), BankUtils.formatCurrency(t.getAmount()), t.getStatus());
    }
}
