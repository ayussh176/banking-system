// File: Account.java
// Responsibility: Abstract base class for all account types

public abstract class Account {
    protected String accountId;
    protected Customer customer;
    protected double balance;

    public Account(String accountId, Customer customer, double initialDeposit) {
        this.accountId = accountId;
        this.customer = customer;
        this.balance = initialDeposit;
    }

    public String getAccountId() { return accountId; }
    public Customer getCustomer() { return customer; }
    public double getBalance() { return balance; }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public boolean withdraw(double amount) {
        if (this.balance >= amount) {
            this.balance -= amount;
            return true;
        }
        return false;
    }
    
    public abstract String getAccountType();
}
