package starter.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import net.serenitybdd.annotations.Managed;
import org.openqa.selenium.WebDriver;

import starter.pages.LoginPage;
import starter.pages.SalesPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SalesSteps {

    @Managed
    WebDriver driver;

    LoginPage loginPage = new LoginPage();
    SalesPage salesPage = new SalesPage();


    @Given("Admin is logged in")
    public void adminIsLoggedIn() {

        // Open login page
        loginPage.openPage();

        // Login with valid credentials
        loginPage.enterUsername("admin");
        loginPage.enterPassword("admin123");
        loginPage.clickLogin();

        // Verify successful login
        assertTrue(
                loginPage.isDashboardDisplayed(),
                "Login failed - Dashboard not visible"
        );
    }


    @And("Admin is on the sales page")
    public void adminIsOnSalesPage() {
        salesPage.openSalesPage();
    }


    @When("Admin clicks Sell Plant")
    public void adminClicksSellPlant() {
        salesPage.clickSellPlant();
    }


    @And("Admin clicks Cancel button")
    public void adminClicksCancelButton() {
        salesPage.clickCancel();
    }


    @Then("Admin should be redirected to sales list page")
    public void verifyRedirectToSalesList() {

        assertTrue(
                salesPage.isOnSalesListPage(),
                "User was NOT redirected to Sales List page"
        );
    }
}
