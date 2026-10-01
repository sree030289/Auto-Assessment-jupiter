package com.planit.jupiter.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ShopPage extends BasePage {
    private final Locator products;

    public ShopPage(Page page) {
        super(page);
        products = page.locator("li.product");
    }

    public ShopPage waitForLoad() {
        assertThat(products.first()).isVisible();
        return this;
    }

    private Locator product(String name) {
        Locator title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName(name).setExact(true));
        return products.filter(new Locator.FilterOptions().setHas(title));
    }

    /** Unit price as advertised on the shop page. */
    public double priceOf(String name) {
        return parsePrice(product(name).locator(".product-price").innerText());
    }

    public void buy(String name, int quantity) {
        Locator buyButton = product(name).getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Buy"));
        for (int i = 0; i < quantity; i++) {
            buyButton.click();
        }
    }

    public CartPage goToCart() {
        nav.cart.click();
        return new CartPage(page).waitForLoad();
    }
}
