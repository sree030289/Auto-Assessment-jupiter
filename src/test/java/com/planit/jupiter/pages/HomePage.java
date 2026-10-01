package com.planit.jupiter.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class HomePage extends BasePage {
    public HomePage(Page page) {
        super(page);
    }

    public HomePage open() {
        page.navigate("/#/home");
        assertThat(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Start Shopping"))).isVisible();
        return this;
    }

    public ContactPage goToContact() {
        nav.contact.click();
        return new ContactPage(page).waitForLoad();
    }

    public ShopPage goToShop() {
        nav.shop.click();
        return new ShopPage(page).waitForLoad();
    }
}
