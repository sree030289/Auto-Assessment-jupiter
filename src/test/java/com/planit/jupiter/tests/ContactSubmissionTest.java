package com.planit.jupiter.tests;

import com.microsoft.playwright.assertions.LocatorAssertions;
import com.planit.jupiter.base.BaseTest;
import com.planit.jupiter.data.ContactDetails;
import com.planit.jupiter.pages.ContactPage;
import com.planit.jupiter.pages.HomePage;
import org.junit.jupiter.api.RepeatedTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

class ContactSubmissionTest extends BaseTest {

    /** Runs 5 times with fresh data and a fresh browser each time; no retries, so all 5 must pass. */
    @RepeatedTest(5)
    void TC2_successfulSubmissionShowsThankYouMessage() {
        ContactDetails contact = ContactDetails.random();
        ContactPage contactPage = new HomePage(page).open().goToContact();

        contactPage.fillMandatoryFields(contact);
        contactPage.submit();

        contactPage.waitForSubmissionSuccess();
        assertThat(contactPage.successAlert).hasText(
                "Thanks " + contact.forename() + ", we appreciate your feedback.",
                new LocatorAssertions.HasTextOptions().setUseInnerText(true));
    }
}
