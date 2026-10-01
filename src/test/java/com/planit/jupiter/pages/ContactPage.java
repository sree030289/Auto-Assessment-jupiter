package com.planit.jupiter.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.options.AriaRole;
import com.planit.jupiter.data.ContactDetails;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ContactPage extends BasePage {
    private final Locator forename;
    private final Locator email;
    private final Locator message;
    private final Locator submitButton;
    public final Locator headerAlert;
    public final Locator successAlert;

    public ContactPage(Page page) {
        super(page);
        forename = page.locator("#forename");
        email = page.locator("#email");
        message = page.locator("#message");
        submitButton = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Submit"));
        headerAlert = page.locator("#header-message .alert");
        successAlert = page.locator(".alert-success");
    }

    public ContactPage waitForLoad() {
        assertThat(forename).isVisible();
        return this;
    }

    /** Validation message next to a mandatory field, e.g. #forename-err. */
    public Locator fieldError(String field) {
        return page.locator("#" + field + "-err");
    }

    public void submit() {
        submitButton.click();
    }

    public void fillMandatoryFields(ContactDetails details) {
        forename.fill(details.forename());
        email.fill(details.email());
        message.fill(details.message());
    }

    /** Submission shows a "Sending Feedback" modal for several seconds first. */
    public void waitForSubmissionSuccess() {
        assertThat(successAlert).isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(30_000));
    }
}
