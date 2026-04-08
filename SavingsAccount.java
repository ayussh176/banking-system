// File: SavingsAccount.java
// Responsibility: Concrete account with interest rate and withdrawal limit

public class SavingsAccount extends Account {
    private double interestRate = 0.04;
    private int monthlyWithdrawalLimit = 5;
    private int withdrawalsThisMonth = 0;

    public SavingsAccount(String accountId, Customer customer, double initialDeposit) {
        super(accountId, customer, initialDeposit);
    }
    
    @Override
    public boolean withdraw(double amount) {
        if (withdrawalsThisMonth >= monthlyWithdrawalLimit) {
            System.out.println("Withdrawal failed: Monthly limit reached for Savings Account.");
            return false;
        }
        boolean success = super.withdraw(amount);
        if (success) {
            withdrawalsThisMonth++;
        }
        return success;
    }

    public void applyMonthlyInterest() {
        double interest = balance * interestRate;
        balance += interest;
        System.out.println("Applied monthly interest: " + BankUtils.formatCurrency(interest));
    }

    @Override
    public String getAccountType() {
        return "Savings";
    }
}
