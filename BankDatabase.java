// File: BankDatabase.java
// DESIGN PATTERN: SINGLETON
// Responsibility: Central repository for all accounts and transactions

// ╔══════════════════════════════════╗
// ║   DESIGN PATTERN: SINGLETON      ║
// ╚══════════════════════════════════╝
// Ensures a single point of truth for all bank accounts and transactions,
// saving memory and avoiding data synchronization issues across multiple instances.

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BankDatabase {
    private static BankDatabase instance;
    private Map<String, Account> accounts;
    private List<Transaction> transactions;

    private BankDatabase() {
        accounts = new HashMap<>();
        transactions = new ArrayList<>();
    }

    public static BankDatabase getInstance() {
        if (instance == null) {
            instance = new BankDatabase();
        }
        return instance;
    }

    public void addAccount(Account account) {
        accounts.put(account.getAccountId(), account);
    }

    public Account getAccount(String accountId) {
        return accounts.get(accountId);
    }

    public List<Account> getAllAccounts() {
        return new ArrayList<>(accounts.values());
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public List<Transaction> getTransactionHistory() {
        return new ArrayList<>(transactions);
    }
}
