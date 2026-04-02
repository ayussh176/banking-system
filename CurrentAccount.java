// File: CurrentAccount.java
// Responsibility: Concrete account with overdraft limit

public class CurrentAccount extends Account {
    private double overdraftLimit = 50000.0;

    public CurrentAccount(String accountId, Customer customer, double initialDeposit) {
        super(accountId, customer, initialDeposit);
    }

    @Override
    public boolean withdraw(double amount) {
        if (balance + overdraftLimit >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public double getAvailableBalance() {
        return balance + overdraftLimit;
    }

    @Override
    public String getAccountType() {
        return "Current";
    }
}
