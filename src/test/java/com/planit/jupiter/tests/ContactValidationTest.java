package com.planit.jupiter.tests;

import com.planit.jupiter.base.BaseTest;
import com.planit.jupiter.data.ContactDetails;
import com.planit.jupiter.pages.ContactPage;
import com.planit.jupiter.pages.HomePage;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

class ContactValidationTest extends BaseTest {
    private static final Map<String, String> EXPECTED_ERRORS = Map.of(
            "forename", "Forename is required",
            "email", "Email is required",
            "message", "Message is required");

    @Test
    void TC1_mandatoryFieldErrorsAppearOnEmptySubmitAndClearOncePopulated() {
        ContactPage contactPage = new HomePage(page).open().goToContact();

        // Submit the empty form and verify the error messages
        contactPage.submit();
        assertThat(contactPage.headerAlert).hasClass(Pattern.compile("alert-error"));
        assertThat(contactPage.headerAlert).containsText("we won't get it unless you complete the form correctly");
        EXPECTED_ERRORS.forEach((field, text) -> assertThat(contactPage.fieldError(field)).hasText(text));

        // Populate mandatory fields and verify the errors are gone
        contactPage.fillMandatoryFields(ContactDetails.random());
        EXPECTED_ERRORS.keySet().forEach(field -> assertThat(contactPage.fieldError(field)).isHidden());
        assertThat(contactPage.headerAlert).not().hasClass(Pattern.compile("alert-error"));
    }
}
