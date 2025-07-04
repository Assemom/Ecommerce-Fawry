import models.*;
import services.CheckoutService;
import exceptions.*;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        // Create products

        //expired and shipped product
        Product cheese = new ExpiredShippedProduct("Cheese", 100, 10,
                LocalDate.now().plusDays(7), 0.2);

        //shipped product
        Product tv = new ShippedProduct("TV", 500, 5, 2.5);

        //expired and shipped product
        Product biscuit = new ExpiredShippedProduct("Biscuit", 150, 8,
                LocalDate.now().plusDays(30), 0.7);

        // not expired and not shipped product
        Product MobileCard = new Product("Mobile Scratch Card", 10, 20);

        // Create customer
        Customer customer = new Customer("Assem Omar", 10000.0);

        // Create cart
        Cart cart = new Cart();

        try {
            // Add items to the cart
            cart.add(cheese, 2);
            cart.add(tv, 3);
            cart.add(MobileCard, 1);
            cart.add(biscuit, 1);

            // Checkout
            CheckoutService.checkout(customer, cart);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        System.out.println();
        System.out.println("\n=== Testing Error Cases Scenarios ===");

        // Test empty cart
        testEmptyCart();

        // Test insufficient balance
        testInsufficientBalance();

        // Test expired product
        testExpiredProduct();

        // Test insufficient stock
        testInsufficientStock();
    }

    private static void testEmptyCart() {
        try {
            Customer customer = new Customer("Assem", 500.0);
            Cart emptyCart = new Cart();
            CheckoutService.checkout(customer, emptyCart);
        } catch (CartEmptyException e) {
            System.out.println("Empty cart test passed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Empty cart test failed: " + e.getMessage());
        }
    }

    private static void testInsufficientBalance() {
        try {
            Customer poorCustomer = new Customer("Poor Assem", 10.0);
            Cart cart = new Cart();
            Product expensiveTV = new ShippedProduct("Expensive TV", 1000, 1, 3.0);
            cart.add(expensiveTV, 1);
            CheckoutService.checkout(poorCustomer, cart);
        } catch (NotEnoughBalanceException e) {
            System.out.println("Insufficient balance test passed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Insufficient balance test failed: " + e.getMessage());
        }
    }

    private static void testExpiredProduct() {
        try {
            Customer customer = new Customer("Assem", 500.0);
            Cart cart = new Cart();
            Product expiredCheese = new ExpiredShippedProduct("Expired Cheese", 100, 5,
                    LocalDate.now().minusDays(1), 0.2);
            cart.add(expiredCheese, 1);
            CheckoutService.checkout(customer, cart);
        } catch (ExpiredProductException e) {
            System.out.println("Expired product test passed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Expired product test failed: " + e.getMessage());
        }
    }

    private static void testInsufficientStock() {
        try {
            Customer customer = new Customer("Assem", 500.0);
            Cart cart = new Cart();
            Product limitedStock = new Product("Limited Item", 50, 2);
            cart.add(limitedStock, 5); // Trying to add more than available
            CheckoutService.checkout(customer, cart);
        } catch (NoAvailableStockException e) {
            System.out.println("Insufficient stock test passed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Insufficient stock test failed: " + e.getMessage());
        }
    }
}