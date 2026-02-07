package starter.stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import net.serenitybdd.annotations.Steps;

import starter.pages.ViewSalesListPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ViewSalesListSteps {

    @Steps
    ViewSalesListPage salesPage;


    @When("User clicks Sales in side navigation")
    public void userClicksSales() {
        System.out.println("Navigating to Sales page");
        // Navigate directly to sales page (session is preserved from @nonadmin hook)
        salesPage.navigateToSalesPage();
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
