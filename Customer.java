// File: Customer.java
// Responsibility: Holds customer information

public class Customer {
    private String customerId;
    private String name;
    private String phone;
    private String email;

    public Customer(String name, String phone, String email) {
        this.customerId = BankUtils.generateId();
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
}
