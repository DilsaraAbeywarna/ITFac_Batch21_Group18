package starter.stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;
import starter.pages.ViewSalesListPage;

import static org.assertj.core.api.Assertions.assertThat;

public class DeleteConfirm {

    @Steps
    ViewSalesListPage viewSalesListPage;

    private String firstSalePlantName;
    private int initialSalesCount;

    @When("Admin clicks Delete button for a sale")
    public void adminClicksDeleteButtonForASale() {
        System.out.println("Clicking Delete button for first sale");
        
        // Store initial count and plant name for later verification
        initialSalesCount = viewSalesListPage.getSalesCount();
        System.out.println("Initial sales count: " + initialSalesCount);
        
        firstSalePlantName = viewSalesListPage.getFirstSalePlantName();
        System.out.println("First sale plant: " + firstSalePlantName);
        
        // Click the delete button
        viewSalesListPage.clickDeleteButtonForFirstSale();
        System.out.println("Clicked delete button");
    }

    @Then("Confirmation dialog should be displayed")
    public void verifyConfirmationDialogDisplayed() {
        System.out.println("Verifying confirmation dialog is displayed");
        boolean isDialogDisplayed = viewSalesListPage.isConfirmationDialogDisplayed();
        System.out.println("Confirmation dialog displayed: " + isDialogDisplayed);
        
        assertThat(isDialogDisplayed)
            .as("Confirmation dialog should be displayed asking 'Are you sure'")
            .isTrue();
    }

    @When("Admin clicks OK on confirmation dialog")
    public void adminClicksOKOnConfirmationDialog() {
        System.out.println("Clicking OK on confirmation dialog");
        viewSalesListPage.acceptConfirmationDialog();
        System.out.println("Confirmation accepted - deletion processed");
    }

    @Then("Sale deleted successfully message should be displayed")
    public void verifySaleDeletedSuccessMessageDisplayed() {
        System.out.println("Verifying 'Sale deleted successfully' message is displayed");
        
        boolean isSuccessMessageDisplayed = viewSalesListPage.isDeleteSuccessMessageDisplayed();
        System.out.println("Success message displayed: " + isSuccessMessageDisplayed);
        
        assertThat(isSuccessMessageDisplayed)
            .as("Sale deleted successfully message should be displayed")
            .isTrue();
    }
}
