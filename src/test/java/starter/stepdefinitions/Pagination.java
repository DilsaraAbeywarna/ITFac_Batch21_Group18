package starter.stepdefinitions;

import io.cucumber.java.en.Then;
import net.serenitybdd.annotations.Steps;
import starter.pages.ViewSalesListPage;

import static org.assertj.core.api.Assertions.assertThat;

public class Pagination {

    @Steps
    ViewSalesListPage viewSalesListPage;

    @Then("Pagination controls should be displayed")
    public void verifyPaginationDisplayed() {
        System.out.println("Verifying pagination controls are displayed");
        boolean isPaginationVisible = viewSalesListPage.isPaginationVisible();
        System.out.println("Pagination visible: " + isPaginationVisible);
        assertThat(isPaginationVisible)
            .as("Pagination controls should be visible")
            .isTrue();
    }
}
