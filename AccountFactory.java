// File: AccountFactory.java
// DESIGN PATTERN: FACTORY
// Responsibility: Creates SavingsAccount, CurrentAccount, or FixedDepositAccount

// ╔══════════════════════════════════╗
// ║   DESIGN PATTERN: FACTORY        ║
// ╚══════════════════════════════════╝
// Ensures clients do not need to know the exact class types they instantiate.
// By centralizing account creation logic here, we decouple the configuration 
// from the use of the objects, making it easy to add new account types later.

public class AccountFactory {
    public static Account createAccount(String type, String accountId, Customer customer, double initialDeposit) {
        switch (type.toLowerCase()) {
            case "savings":
                return new SavingsAccount(accountId, customer, initialDeposit);
            case "current":
                return new CurrentAccount(accountId, customer, initialDeposit);
            case "fixed":
                return new FixedDepositAccount(accountId, customer, initialDeposit);
            default:
                throw new IllegalArgumentException("Unknown account type: " + type);
        }
    }
}
