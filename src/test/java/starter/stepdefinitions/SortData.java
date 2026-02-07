package starter.stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;
import starter.pages.ViewSalesListPage;

import static org.assertj.core.api.Assertions.assertThat;

public class SortData {

    @Steps
    ViewSalesListPage viewSalesListPage;

    @Then("Sales should be sorted by Sold Date in descending order")
    public void verifySalesSortedByDateDescending() {
        System.out.println("Verifying sales are sorted by Sold Date descending");
        boolean isSortedDescending = viewSalesListPage.areSalesSortedByDateDescending();
        System.out.println("Sales sorted by date descending: " + isSortedDescending);
        assertThat(isSortedDescending)
            .as("Sales should be sorted by Sold Date in descending order (newest first)")
            .isTrue();
    }

    @When("User clicks Quantity column header")
    public void userClicksQuantityColumnHeader() {
        System.out.println("Clicking Quantity column header");
        viewSalesListPage.clickQuantityColumnHeader();
    }

    @When("User clicks Quantity column header again")
    public void userClicksQuantityColumnHeaderAgain() {
        System.out.println("Clicking Quantity column header again");
        viewSalesListPage.clickQuantityColumnHeader();
    }

    @Then("Sales should be sorted by Quantity in ascending order")
    public void verifySalesSortedByQuantityAscending() {
        System.out.println("Verifying sales are sorted by Quantity ascending");
        boolean isSortedAscending = viewSalesListPage.areSalesSortedByQuantityAscending();
        System.out.println("Sales sorted by quantity ascending: " + isSortedAscending);
        assertThat(isSortedAscending)
            .as("Sales should be sorted by Quantity in ascending order")
            .isTrue();
    }

    @Then("Sales should be sorted by Quantity in descending order")
    public void verifySalesSortedByQuantityDescending() {
        System.out.println("Verifying sales are sorted by Quantity descending");
        boolean isSortedDescending = viewSalesListPage.areSalesSortedByQuantityDescending();
        System.out.println("Sales sorted by quantity descending: " + isSortedDescending);
        assertThat(isSortedDescending)
            .as("Sales should be sorted by Quantity in descending order")
            .isTrue();
    }
}
