package utils;

import net.serenitybdd.core.pages.PageObject;
import starter.pages.LoginPage;

/**
 * Utility class for UI-based login functionality.
 * This class encapsulates the login flow using the LoginPage.
 */
public class LoginUtil {

    private LoginPage loginPage;

    /**
     * Constructor that initializes LoginPage with the provided PageObject's driver.
     *
     * @param pageObject The PageObject instance that provides the WebDriver
     */
    public LoginUtil(PageObject pageObject) {
        this.loginPage = new LoginPage();
        this.loginPage.setDriver(pageObject.getDriver());
    }

    /**
     * Performs a UI-based login using the provided credentials.
     *
     * @param username The username to login with
     * @param password The password to login with
     */
    public void loginViaUI(String username, String password) {
        // Navigate to login page
        loginPage.openPage();
        System.out.println("Navigated to login page: " + loginPage.getDriver().getCurrentUrl());

        // Enter credentials
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);

        // Click login button
        loginPage.clickLogin();

        // Give the form a moment to submit
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Login submitted. Current URL: " + loginPage.getDriver().getCurrentUrl());
    }
}