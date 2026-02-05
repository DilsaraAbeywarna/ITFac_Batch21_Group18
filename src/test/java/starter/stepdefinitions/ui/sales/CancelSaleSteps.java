package starter.stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import net.serenitybdd.annotations.Steps;

import starter.pages.ViewSalesListPage;
import starter.pages.SellPlantPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CancelSaleSteps {

    @Steps
    ViewSalesListPage salesListPage;

    @Steps
    SellPlantPage sellPlantPage;


    @When("Admin clicks Sales in side navigation")
    public void adminClicksSalesNavigation() {
        System.out.println("Navigating to Sales page");
        salesListPage.navigateToSalesPage();
    }


    @When("Admin clicks Sell Plant button")
    public void adminClicksSellPlant() {
        System.out.println("Navigating to Sell Plant page");
        salesListPage.navigateToSellPlantPage();
    }


    @Then("Sell Plant page should be displayed")
    public void verifySellPlantPageDisplayed() {
        assertTrue(
                sellPlantPage.isOnSellPlantPage(),
                "Sell Plant page did not load"
        );
    }


    @When("Admin clicks Cancel button")
    public void adminClicksCancel() {
        System.out.println("Clicking Cancel button");
        sellPlantPage.clickCancel();
    }


    @Then("Admin should be redirected to Sales list page")
    public void verifyRedirectedToSalesList() {
        assertTrue(
                salesListPage.isOnSalesPage(),
                "Admin was not redirected to Sales list page"
        );
    }
}
