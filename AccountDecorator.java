// File: AccountDecorator.java
// DESIGN PATTERN: DECORATOR
// Responsibility: Abstract class for adding features to accounts dynamically

// ╔══════════════════════════════════╗
// ║   DESIGN PATTERN: DECORATOR      ║
// ╚══════════════════════════════════╝
// Structural Pattern: Allows behavior to be added to an individual object, 
// either statically or dynamically, without affecting the behavior of other 
// objects from the same class. In SmartBank, this enables "wrapping" any 
// account with additional services like insurance or priority status.

public abstract class AccountDecorator extends Account {
    protected Account decoratedAccount;

    public AccountDecorator(Account account) {
        // Pass base info to super, though we will delegate most calls
        super(account.getAccountId(), account.getCustomer(), account.getBalance());
        this.decoratedAccount = account;
    }

    @Override
    public String getAccountId() {
        return decoratedAccount.getAccountId();
    }

    @Override
    public Customer getCustomer() {
        return decoratedAccount.getCustomer();
    }

    @Override
    public double getBalance() {
        return decoratedAccount.getBalance();
    }

    @Override
    public void deposit(double amount) {
        decoratedAccount.deposit(amount);
    }

    @Override
    public boolean withdraw(double amount) {
        return decoratedAccount.withdraw(amount);
    }

    @Override
    public abstract String getAccountType();
}
