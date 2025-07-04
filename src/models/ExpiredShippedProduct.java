package models;

import interfaces.Expired;
import interfaces.Shipped;
import interfaces.ShippedItems;
import java.time.LocalDate;

public class ExpiredShippedProduct extends Product implements Expired, Shipped, ShippedItems {
    private LocalDate expirationDate;
    private double weight;

    public ExpiredShippedProduct(String name, double price, int quantity,
                                 LocalDate expirationDate, double weight) {
        super(name, price, quantity);
        this.expirationDate = expirationDate;
        this.weight = weight;
    }
    @Override
    public LocalDate getExpirationDate() {
        return expirationDate;
    }
    @Override
    public boolean isExpired() {
        return LocalDate.now().isAfter(expirationDate);
    }
    @Override
    public double getWeight() {
        return weight;
    }
    @Override
    public boolean requiresShipping() {
        return true;
    }

}
