package starter.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import net.serenitybdd.annotations.Managed;
import org.openqa.selenium.WebDriver;

import starter.pages.LoginPage;
import starter.pages.ViewSalesListPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ViewSalesListSteps {

    @Managed
    WebDriver driver;

    LoginPage loginPage = new LoginPage();
    ViewSalesListPage salesPage = new ViewSalesListPage();


    @Given("Test user is logged in")
    public void testUserIsLoggedIn() {

        // Open login page
        loginPage.openPage();

        // Login
        loginPage.enterUsername("testuser");
        loginPage.enterPassword("test123");
        loginPage.clickLogin();

        // Verify login success
        assertTrue(
                loginPage.isDashboardDisplayed(),
                "Login failed for test user"
        );
    }


    @When("User clicks Sales in side navigation")
    public void userClicksSales() {

        salesPage.clickSalesMenu();
    }


    @Then("Sales page should load successfully")
    public void verifySalesPageLoaded() {

        assertTrue(
                salesPage.isOnSalesPage(),
                "Sales page did not load"
        );
    }


    @Then("Sales list should be visible")
    public void verifySalesListVisible() {

        assertTrue(
                salesPage.isSalesTableVisible(),
                "Sales table is not visible"
        );

        assertTrue(
                salesPage.hasSalesRecords(),
                "No sales records found"
        );
    }
}
