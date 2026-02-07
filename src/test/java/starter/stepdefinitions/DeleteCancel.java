package starter.stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;
import starter.pages.ViewSalesListPage;

import static org.assertj.core.api.Assertions.assertThat;

public class DeleteCancel {

    @Steps
    ViewSalesListPage viewSalesListPage;

    private String firstSalePlantName;
    private int initialSalesCount;

    @When("Admin clicks Delete button to test cancel")
    public void adminClicksDeleteButtonToTestCancel() {
        System.out.println("Clicking Delete button for first sale (to test cancel)");
        // Count sales before deletion attempt
        initialSalesCount = viewSalesListPage.getSalesCount();
        System.out.println("Initial sales count: " + initialSalesCount);
        
        // Store the plant name before attempting delete
        firstSalePlantName = viewSalesListPage.getFirstSalePlantName();
        System.out.println("First sale plant: " + firstSalePlantName);
        viewSalesListPage.clickDeleteButtonForFirstSale();
    }

    @Then("Delete confirmation dialog is displayed")
    public void verifyDeleteConfirmationDialogDisplayed() {
        System.out.println("Verifying delete confirmation dialog is displayed");
        boolean isDialogDisplayed = viewSalesListPage.isConfirmationDialogDisplayed();
        System.out.println("Delete confirmation dialog displayed: " + isDialogDisplayed);
        assertThat(isDialogDisplayed)
            .as("Delete confirmation dialog should be displayed")
            .isTrue();
    }

    @When("Admin cancels the delete confirmation")
    public void adminCancelsTheDeleteConfirmation() {
        System.out.println("Clicking Cancel on delete confirmation dialog");
        viewSalesListPage.dismissConfirmationDialog();
    }

    @Then("Sale item remains in the list")
    public void verifySaleItemRemainsInTheList() {
        System.out.println("Verifying sale item remains in the list after cancel");
        boolean itemExists = viewSalesListPage.doesSaleExistByPlantName(firstSalePlantName);
        System.out.println("Sale with plant '" + firstSalePlantName + "' exists: " + itemExists);
        assertThat(itemExists)
            .as("Sale item should remain in the list after canceling delete")
            .isTrue();
    }
}
