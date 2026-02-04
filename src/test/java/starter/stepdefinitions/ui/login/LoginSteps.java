package starter.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import starter.pages.LoginPage;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.annotations.Steps;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginSteps {
    @Managed
    WebDriver driver;
    
    @Steps
    LoginPage loginPage;

    @Given("Admin is on the login page")
    public void adminIsOnLoginPage() {
        loginPage.setDriver(driver);
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
}