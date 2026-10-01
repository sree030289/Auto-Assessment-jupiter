package com.planit.jupiter.tests;

import com.planit.jupiter.base.BaseTest;
import com.planit.jupiter.data.Product;
import com.planit.jupiter.pages.CartPage;
import com.planit.jupiter.pages.CartPage.CartLine;
import com.planit.jupiter.pages.HomePage;
import com.planit.jupiter.pages.ShopPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CartTest extends BaseTest {
    private static final double DELTA = 0.001;

    @Test
    void TC3_cartPricesSubtotalsAndTotalAreCorrect() {
        ShopPage shopPage = new HomePage(page).open().goToShop();

        // Buy 2 Stuffed Frog, 5 Fluffy Bunny, 3 Valentine Bear
        Map<String, Double> shopPrices = new HashMap<>();
        int totalQuantity = 0;
        for (Product item : Product.CART_SCENARIO) {
            shopPrices.put(item.name(), shopPage.priceOf(item.name()));
            shopPage.buy(item.name(), item.quantity());
            totalQuantity += item.quantity();
        }
        assertThat(shopPage.nav.cartCount).hasText(String.valueOf(totalQuantity));

        // Go to the cart page
        CartPage cartPage = shopPage.goToCart();
        assertThat(cartPage.rows).hasCount(Product.CART_SCENARIO.size());

        // Verify price and subtotal for each product (assertAll reports every failure, not just the first)
        List<Executable> checks = new ArrayList<>();
        for (Product item : Product.CART_SCENARIO) {
            CartLine line = cartPage.line(item.name());
            checks.add(() -> assertEquals(item.quantity(), line.quantity(), item.name() + " quantity"));
            checks.add(() -> assertEquals(item.expectedPrice(), line.price(), DELTA, item.name() + " price vs spec"));
            checks.add(() -> assertEquals(shopPrices.get(item.name()), line.price(), DELTA, item.name() + " price vs shop listing"));
            checks.add(() -> assertEquals(line.price() * line.quantity(), line.subtotal(), DELTA, item.name() + " subtotal = price x qty"));
        }
        assertAll(checks);

        // Verify total = sum(subtotals)
        double sumOfSubtotals = cartPage.lines().stream().mapToDouble(CartLine::subtotal).sum();
        assertEquals(sumOfSubtotals, cartPage.getTotal(), DELTA, "total = sum of subtotals");
    }
}
