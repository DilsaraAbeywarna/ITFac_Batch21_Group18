package starter.stepdefinitions;

import io.cucumber.java.en.Then;
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
}
