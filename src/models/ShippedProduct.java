package models;

import interfaces.Shipped;
import interfaces.ShippedItems;

public class ShippedProduct extends Product implements Shipped, ShippedItems{
    private double weight;

    public ShippedProduct(String name, double price, int quantity, double weight) {
        super(name, price, quantity);
        this.weight = weight;
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
