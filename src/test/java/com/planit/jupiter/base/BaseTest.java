package com.planit.jupiter.base;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * Starts a fresh browser per test so tests never share state (the cart lives in the browser).
 * Override with -Dbrowser=firefox|webkit and -DbaseUrl=https://...
 */
public abstract class BaseTest {
    private static final String BASE_URL =
            System.getProperty("baseUrl", "http://jupiter.cloud.planittesting.com");
    private static final String BROWSER = System.getProperty("browser", "chromium");
    private static final boolean HEADED = Boolean.getBoolean("headed");

    private Playwright playwright;
    private Browser browser;
    protected Page page;

    @BeforeEach
    void launchBrowser() {
        playwright = Playwright.create();
        BrowserType type = switch (BROWSER) {
            case "firefox" -> playwright.firefox();
            case "webkit" -> playwright.webkit();
            default -> playwright.chromium();
        };
        browser = type.launch(new BrowserType.LaunchOptions().setHeadless(!HEADED));
        page = browser.newContext(new Browser.NewContextOptions().setBaseURL(BASE_URL)).newPage();
    }

    @AfterEach
    void closeBrowser() {
        browser.close();
        playwright.close();
    }
}
