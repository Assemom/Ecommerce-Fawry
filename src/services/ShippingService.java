package services;

import exceptions.CartEmptyException;
import interfaces.ShippedItems;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ShippingService {
    private static final double SHIPPING_RATE_PER_KG = 11.0;
    private static final double SHIPPING_FEE = 4.0;

    public static double calculateShippingFee(List<ShippedItems> items) {
        if (items.isEmpty()) {
            return 0.0;
        }
        double totalWeight = items.stream().mapToDouble(ShippedItems::getWeight).sum();
        return SHIPPING_FEE + (totalWeight * SHIPPING_RATE_PER_KG);
    }


    public static void processShipment(List<ShippedItems> items) {
        if (items.isEmpty()) {
            try {
                throw new CartEmptyException("Empty cart add Products in it to proceed");
            } catch (CartEmptyException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("** Shipment notice **");

        // Group items by name and their total weight by using hashmaps
        Map<String, Integer> itemCounts = new HashMap<>();
        Map<String, Double> itemWeights = new HashMap<>();

        for (ShippedItems item : items) {
            String name = item.getName();
            itemCounts.put(name, itemCounts.getOrDefault(name, 0) + 1);
            itemWeights.put(name, item.getWeight());
        }

        double totalWeight = 0.0;
        for (Map.Entry<String, Integer> entry : itemCounts.entrySet()) {
            String name = entry.getKey();
            int count = entry.getValue();
            double weight = itemWeights.get(name) * count;
            totalWeight += weight;
            System.out.printf("%dx %s %.0fg%n", count, name, weight * 1000);
        }

        System.out.printf("Total package weight %.1fkg%n", totalWeight);
    }
}