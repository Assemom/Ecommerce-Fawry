package services;

import models.*;
import interfaces.Expired;
import interfaces.ShippedItems;
import exceptions.*;
import java.util.List;

public class CheckoutService {

    public static void checkout(Customer customer, Cart cart) {

        // Check if cart is empty or not
        if (cart.isEmpty()) {
            throw new CartEmptyException("Cart is empty");
        }

        validateCartItems(cart);

        // Calculations to proceed
        double subtotal = cart.getSubtotal();
        List<ShippedItems> shippableItems = cart.getShippedItems();
        double shippingFee = ShippingService.calculateShippingFee(shippableItems);
        double totalAmount = subtotal + shippingFee;

        // Check customer balance
        if (!customer.hasEnoughBalance(totalAmount)) {
            throw new NotEnoughBalanceException("No Available balance. Required: $" + totalAmount +
                    ", Available: $" + customer.getBalance());
        }

        ShippingService.processShipment(shippableItems);
        processPayment(customer, cart, subtotal, shippingFee, totalAmount);
    }

    private static void validateCartItems(Cart cart) {
        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();

            // Check if product is expired
            if (product instanceof Expired && ((Expired) product).isExpired()) {
                throw new ExpiredProductException("Product " + product.getName() + " has expired");
            }

            // Check if enough stock is available
            if (!product.isInStock(item.getQuantity())) {
                throw new NoAvailableStockException("Not enough stock for " + product.getName());
            }
        }
    }

    private static void processPayment(Customer customer, Cart cart,double subtotal, double shippingFee, double totalAmount) {
        for (CartItem item : cart.getItems()) {
            item.getProduct().reduceStock(item.getQuantity());
        }
        customer.reduceBalance(totalAmount);
        printReceipt(cart, subtotal, shippingFee, totalAmount, customer.getBalance());
    }

    private static void printReceipt(Cart cart, double subtotal, double shippingFee,
                                     double totalAmount, double remainingBalance) {
        System.out.println();
        System.out.println("** Checkout receipt **");

        for (CartItem item : cart.getItems()) {
            System.out.printf("%dx %s %.0f%n",
                    item.getQuantity(),
                    item.getProduct().getName(),
                    item.getTotalPrice());
        }

        System.out.println("----------------------");
        System.out.printf("Subtotal %.0f%n", subtotal);
        System.out.printf("Shipping %.0f%n", shippingFee);
        System.out.printf("Amount %.0f%n", totalAmount);
    }
}