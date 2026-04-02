// File: Main.java
// Responsibility: Entry point, demonstrates all 3 patterns

public class Main {
    public static void main(String[] args) {
        BankUtils.printDivider("Step 1 - SINGLETON: Getting BankDatabase instance");
        // ╔══════════════════════════════════╗
        // ║   DESIGN PATTERN: SINGLETON      ║
        // ╚══════════════════════════════════╝
        // Demonstrating that multiple calls return the same single instance.
        BankDatabase db1 = BankDatabase.getInstance();
        BankDatabase db2 = BankDatabase.getInstance();
        System.out.println("db1 reference: " + db1.hashCode());
        System.out.println("db2 reference: " + db2.hashCode());
        System.out.println("Are db1 and db2 the same object? " + (db1 == db2));

        BankUtils.printDivider("Step 2 - FACTORY: Create Customers and Accounts");
        // ╔══════════════════════════════════╗
        // ║   DESIGN PATTERN: FACTORY        ║
        // ╚══════════════════════════════════╝
        // Demonstrating dynamic creation of different concrete objects via a common Factory method.
        Customer c1 = new Customer("Alice Smith", "555-1234", "alice@example.com");
        Customer c2 = new Customer("Bob Johnson", "555-5678", "bob@example.com");
        Customer c3 = new Customer("Charlie Brown", "555-9012", "charlie@example.com");

        Account savings = AccountFactory.createAccount("savings", "ACC-1001", c1, 5000.0);
        Account current = AccountFactory.createAccount("current", "ACC-1002", c2, 10000.0);
        Account fixed = AccountFactory.createAccount("fixed", "ACC-1003", c3, 20000.0);

        System.out.println("Created: " + savings.getAccountType() + " | " + savings.getAccountId() + " | Current Balance: " + BankUtils.formatCurrency(savings.getBalance()));
        System.out.println("Created: " + current.getAccountType() + " | " + current.getAccountId() + " | Current Balance: " + BankUtils.formatCurrency(current.getBalance()));
        System.out.println("Created: " + fixed.getAccountType() + " | " + fixed.getAccountId() + " | Current Balance: " + BankUtils.formatCurrency(fixed.getBalance()));

        BankUtils.printDivider("Step 3 - Register all 3 accounts in BankDatabase");
        db1.addAccount(savings);
        db1.addAccount(current);
        db1.addAccount(fixed);
        System.out.println("All accounts successfully registered.");

        BankUtils.printDivider("Step 4 - OBSERVER: Attach SMSAlert, EmailAlert, FraudDetectionService to each Transaction");
        // ╔══════════════════════════════════╗
        // ║   DESIGN PATTERN: OBSERVER       ║
        // ╚══════════════════════════════════╝
        // Instantiating observers. They will be dynamically attached to each transaction 
        // to subscribe to the transaction events.
        SMSAlert smsAlert = new SMSAlert();
        EmailAlert emailAlert = new EmailAlert();
        FraudDetectionService fraudDetection = new FraudDetectionService();
        System.out.println("Observers instantiated: SMSAlert, EmailAlert, FraudDetectionService");

        BankUtils.printDivider("Step 5 - Perform 5 transactions");

        // 1. Deposit Rs. 10,000 into savings
        System.out.println("\n--- Transaction 1: Deposit Rs. 10,000 into savings ---");
        Transaction t1 = new Transaction(null, savings, 10000.0, TransactionType.DEPOSIT);
        t1.addObserver(smsAlert); t1.addObserver(emailAlert); t1.addObserver(fraudDetection);
        t1.execute();
        db1.addTransaction(t1);

        // 2. Withdraw Rs. 3,000 from savings
        System.out.println("\n--- Transaction 2: Withdraw Rs. 3,000 from savings ---");
        Transaction t2 = new Transaction(savings, null, 3000.0, TransactionType.WITHDRAWAL);
        t2.addObserver(smsAlert); t2.addObserver(emailAlert); t2.addObserver(fraudDetection);
        t2.execute();
        db1.addTransaction(t2);

        // 3. Transfer Rs. 7,500 from savings to current
        System.out.println("\n--- Transaction 3: Transfer Rs. 7,500 from savings to current ---");
        Transaction t3 = new Transaction(savings, current, 7500.0, TransactionType.TRANSFER);
        t3.addObserver(smsAlert); t3.addObserver(emailAlert); t3.addObserver(fraudDetection);
        t3.execute();
        db1.addTransaction(t3);

        // 4. Attempt withdrawal beyond limit (should FAIL with message)
        System.out.println("\n--- Transaction 4: Attempt withdrawal beyond available balance ---");
        // Savings has initial 5000 + 10000 deposit - 3000 withdrawal - 7500 transfer = 4500. Let's withdraw 100000.
        Transaction t4 = new Transaction(savings, null, 100000.0, TransactionType.WITHDRAWAL);
        t4.addObserver(smsAlert); t4.addObserver(emailAlert); t4.addObserver(fraudDetection);
        t4.execute();
        db1.addTransaction(t4);

        // 5. Large deposit Rs. 75,000 into current (should be FLAGGED by fraud detection)
        System.out.println("\n--- Transaction 5: Large deposit Rs. 75,000 into current ---");
        Transaction t5 = new Transaction(null, current, 75000.0, TransactionType.DEPOSIT);
        t5.addObserver(smsAlert); t5.addObserver(emailAlert); t5.addObserver(fraudDetection);
        t5.execute();
        db1.addTransaction(t5);

        BankUtils.printDivider("Step 6 - Print full transaction history from BankDatabase");
        for (Transaction t : db1.getTransactionHistory()) {
            String from = t.getFromAccount() != null ? t.getFromAccount().getAccountId() : "EXT";
            String to = t.getToAccount() != null ? t.getToAccount().getAccountId() : "EXT";
            System.out.printf("Tx %s | %-10s | %s -> %s | Amount: %s | Status: %s%n", 
                              t.getTransactionId(), t.getType(), from, to, BankUtils.formatCurrency(t.getAmount()), t.getStatus());
        }

        BankUtils.printDivider("Step 7 - Print all account balances");
        for (Account a : db1.getAllAccounts()) {
            System.out.printf("Account: %s | Type: %-15s | Customer: %-15s | Balance: %s%n",
                              a.getAccountId(), a.getAccountType(), a.getCustomer().getName(), BankUtils.formatCurrency(a.getBalance()));
        }
    }
}
