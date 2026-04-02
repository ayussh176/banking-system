// File: BankUtils.java
// Responsibility: Utility class for formatting and ID generation

import java.util.UUID;

public class BankUtils {
    public static String generateId() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    public static String formatCurrency(double amount) {
        return String.format("Rs. %,.2f", amount);
    }
    
    public static void printDivider(String title) {
        System.out.println("\n========================================");
        System.out.println("  " + title);
        System.out.println("========================================");
    }
}
