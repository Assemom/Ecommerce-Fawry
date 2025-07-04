package models;

public class Customer {
    private String name;
    private double balance;

    public Customer(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public boolean hasEnoughBalance(double amount) {
        return this.balance >= amount;
    }

    public void reduceBalance(double amount) {
        // check for balance
        if(hasEnoughBalance(amount)) {
            this.balance -= amount;
        }
    }

    public String getName() {
        return name;
    }
    public double getBalance() {
        return balance;
    }

}
