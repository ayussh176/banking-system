// File: InsuranceDecorator.java
// DESIGN PATTERN: DECORATOR
// Responsibility: Adds insurance coverage details to an account

// ╔══════════════════════════════════╗
// ║   DESIGN PATTERN: DECORATOR      ║
// ╚══════════════════════════════════╝
// Concrete Decorator: Enhances the account by adding "Insured" to its type 
// and could potentially implement additional logic. This shows how we can 
// structure objects to gain functionality at runtime.

public class InsuranceDecorator extends AccountDecorator {
    private double insurancePremium = 500.0;

    public InsuranceDecorator(Account account) {
        super(account);
    }

    @Override
    public String getAccountType() {
        return decoratedAccount.getAccountType() + " (Insured)";
    }

    // Example of added behavior
    public void applyInsurancePremium() {
        if (decoratedAccount.withdraw(insurancePremium)) {
            System.out.println("Insurance premium of " + BankUtils.formatCurrency(insurancePremium) + " deducted.");
        } else {
            System.out.println("Insufficient balance to deduct insurance premium.");
        }
    }
}
