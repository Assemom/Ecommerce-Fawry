package models;

import interfaces.Expired;
import interfaces.Shipped;
import interfaces.ShippedItems;
import exceptions.*;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void add(Product product, int quantity) throws NoAvailableStockException, ExpiredProductException {
        // Check if product is expired or not
        if (product instanceof Expired && ((Expired) product).isExpired()) {
            throw new ExpiredProductException("Product " + product.getName() + " has expired");
        }
        // Check if enough stock is available or not
        if (!product.isInStock(quantity)) {
            throw new NoAvailableStockException("No Available stock for " + product.getName());
        }
        items.add(new CartItem(product, quantity));
    }

    public List<CartItem> getItems() {
        return new ArrayList<>(items);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public double getSubtotal() {
        return items.stream().mapToDouble(CartItem::getTotalPrice).sum();
    }

    public List<ShippedItems> getShippedItems() {
        List<ShippedItems> ShippedItems = new ArrayList<>();

        for (CartItem item : items) {
            if (item.getProduct() instanceof Shipped) {
                for (int i = 0; i < item.getQuantity(); i++) {
                    ShippedItems.add((ShippedItems) item.getProduct());
                }
            }
        }

        return ShippedItems;
    }
}
