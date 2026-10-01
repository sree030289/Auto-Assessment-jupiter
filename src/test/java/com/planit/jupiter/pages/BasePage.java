package com.planit.jupiter.pages;

import com.microsoft.playwright.Page;

/**
 * Common behaviour for all pages. Pages move between each other through the in-app nav
 * links: the cart is held in memory by the Angular app, so a full page load would empty it.
 */
public abstract class BasePage {
    protected final Page page;
    public final NavBar nav;

    protected BasePage(Page page) {
        this.page = page;
        this.nav = new NavBar(page);
    }

    /** Turns text like "$10.99" or "Total: 31.97" into the number 10.99 / 31.97. */
    protected double parsePrice(String text) {
        return Double.parseDouble(text.replaceAll("[^0-9.]", ""));
    }
}
