// File: FixedDepositAccount.java
// Responsibility: Concrete account with lock-in period and maturity date

import java.time.LocalDate;

public class FixedDepositAccount extends Account {
    private LocalDate maturityDate;
    private double penaltyRate = 0.02;
    private double interestRate = 0.07;

    public FixedDepositAccount(String accountId, Customer customer, double initialDeposit) {
        super(accountId, customer, initialDeposit);
        this.maturityDate = LocalDate.now().plusYears(1); // 1 year lock-in
    }

    @Override
    public boolean withdraw(double amount) {
        if (LocalDate.now().isBefore(maturityDate)) {
            System.out.println("Early withdrawal warning: Penalty applied.");
            double penalty = amount * penaltyRate;
            if (balance >= (amount + penalty)) {
                balance -= (amount + penalty);
                return true;
            }
            return false;
        }
        return super.withdraw(amount);
    }

    public double calculateMaturityAmount() {
        return balance + (balance * interestRate);
    }

    @Override
    public String getAccountType() {
        return "Fixed Deposit";
    }
}
