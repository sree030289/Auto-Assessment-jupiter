package com.planit.jupiter.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CartPage extends BasePage {
    public record CartLine(String name, double price, int quantity, double subtotal) {}

    public final Locator rows;
    private final Locator total;

    public CartPage(Page page) {
        super(page);
        rows = page.locator("table.cart-items tbody tr.cart-item");
        total = page.locator("table.cart-items .total");
    }

    public CartPage waitForLoad() {
        assertThat(rows.first()).isVisible();
        return this;
    }

    public CartLine line(String name) {
        Locator row = rows.filter(new Locator.FilterOptions().setHasText(name));
        assertThat(row).hasCount(1);
        Locator cells = row.locator("td");
        return new CartLine(
                name,
                parsePrice(cells.nth(1).innerText()),
                Integer.parseInt(cells.nth(2).locator("input").inputValue()),
                parsePrice(cells.nth(3).innerText()));
    }

    public List<CartLine> lines() {
        return rows.locator("td:first-child").allInnerTexts().stream()
                .map(String::trim)
                .map(this::line)
                .toList();
    }

    public double getTotal() {
        return parsePrice(total.innerText());
    }
}
