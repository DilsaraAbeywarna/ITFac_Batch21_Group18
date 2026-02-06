package starter.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import starter.pages.LoginPage;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.annotations.Steps;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginSteps {
    private static final Logger logger = LoggerFactory.getLogger(LoginSteps.class);

    @Managed
    WebDriver driver;
    
    @Steps
    LoginPage loginPage;

    // ==================== Admin Login Feature Steps ====================

    @Given("Admin is on the login page")
    public void adminIsOnLoginPage() {
        loginPage.openPage();
    }

    @When("Admin enters username {string} and password {string}")
    public void enterCredentials(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();
    }

    @Then("Admin should see the dashboard")
    public void verifyDashboard() {
        assertTrue(loginPage.isDashboardDisplayed(), "Dashboard is not visible");
    }

    @Then("Admin should see error message {string}")
    public void verifyError(String message) {
        assertEquals(message, loginPage.getErrorMessage());
    }

    // ==================== Shared Login Steps (For Plant Tests) ====================

    @Given("Normal User is logged in")
    public void normalUserIsLoggedIn() {
        loginPage.openPage();
        loginPage.enterUsername("testuser");
        loginPage.enterPassword("test123");
        loginPage.clickLogin();
        loginPage.waitForSuccessfulLogin();
        logger.info("Normal User logged in successfully");
    }

    @Given("Admin is logged in")
    public void adminIsLoggedIn() {
        loginPage.openPage();
        loginPage.enterUsername("admin");
        loginPage.enterPassword("admin123");
        loginPage.clickLogin();
        loginPage.waitForSuccessfulLogin();
        logger.info("Admin logged in successfully");
    }
}