package models;

import interfaces.Expired;

import java.time.LocalDate;

public class ExpiredProduct extends Product implements Expired {
    private LocalDate expirationDate;

    public ExpiredProduct(String name, double price, int quantity, LocalDate expirationDate) {
        super(name, price, quantity);
        this.expirationDate = expirationDate;
    }
    @Override
    public LocalDate getExpirationDate() {
        return expirationDate;
    }
    @Override
    public boolean isExpired() {
        return LocalDate.now().isAfter(expirationDate);
    }
    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
}
