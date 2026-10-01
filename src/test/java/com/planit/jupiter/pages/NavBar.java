package com.planit.jupiter.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/** Top navigation bar, shared by every page. */
public class NavBar {
    public final Locator contact;
    public final Locator shop;
    public final Locator cart;
    public final Locator cartCount;

    public NavBar(Page page) {
        contact = page.locator("#nav-contact a");
        shop = page.locator("#nav-shop a");
        cart = page.locator("#nav-cart a");
        cartCount = page.locator("#nav-cart .cart-count");
    }
}
