package com.planit.jupiter.data;

import java.util.List;

/** An item to buy, with the unit price the test case expects. */
public record Product(String name, int quantity, double expectedPrice) {
    public static final List<Product> CART_SCENARIO = List.of(
            new Product("Stuffed Frog", 2, 10.99),
            new Product("Fluffy Bunny", 5, 9.99),
            new Product("Valentine Bear", 3, 14.99));
}
