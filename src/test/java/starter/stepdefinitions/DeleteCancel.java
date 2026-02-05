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

    @When("Admin clicks Delete button for first sale")
    public void adminClicksDeleteButtonForFirstSale() {
        System.out.println("Clicking Delete button for first sale");
        // Count sales before deletion
        initialSalesCount = viewSalesListPage.getSalesCount();
        System.out.println("Initial sales count: " + initialSalesCount);
        
        // Store the plant name before attempting delete
        firstSalePlantName = viewSalesListPage.getFirstSalePlantName();
        System.out.println("First sale plant: " + firstSalePlantName);
        viewSalesListPage.clickDeleteButtonForFirstSale();
    }

    @Then("Confirmation dialog should be displayed")
    public void verifyConfirmationDialogDisplayed() {
        System.out.println("Verifying confirmation dialog is displayed");
        boolean isDialogDisplayed = viewSalesListPage.isConfirmationDialogDisplayed();
        System.out.println("Confirmation dialog displayed: " + isDialogDisplayed);
        assertThat(isDialogDisplayed)
            .as("Confirmation dialog should be displayed")
            .isTrue();
    }

    @When("Admin clicks Cancel on confirmation dialog")
    public void adminClicksCancelOnDialog() {
        System.out.println("Clicking Cancel on confirmation dialog");
        viewSalesListPage.dismissConfirmationDialog();
    }

    @Then("Sale item should remain in the list")
    public void verifySaleItemRemainsInList() {
        System.out.println("Verifying sale item remains in the list");
        boolean itemExists = viewSalesListPage.doesSaleExistByPlantName(firstSalePlantName);
        System.out.println("Sale with plant '" + firstSalePlantName + "' exists: " + itemExists);
        assertThat(itemExists)
            .as("Sale item should remain in the list after canceling delete")
            .isTrue();
    }

    @When("Admin clicks OK on confirmation dialog")
    public void adminClicksOKOnDialog() {
        System.out.println("Clicking OK on confirmation dialog");
        viewSalesListPage.acceptConfirmationDialog();
    }

    @Then("Sale item should be deleted from the list")
    public void verifySaleItemDeletedFromList() {
        System.out.println("Verifying sale item is deleted from the list");
        int currentSalesCount = viewSalesListPage.getSalesCount();
        System.out.println("Current sales count: " + currentSalesCount);
        System.out.println("Initial sales count: " + initialSalesCount);
        
        // Verify count decreased by 1
        assertThat(currentSalesCount)
            .as("Sales count should decrease by 1 after deletion")
            .isEqualTo(initialSalesCount - 1);
    }

    @Then("Success message should be displayed")
    public void verifySuccessMessageDisplayed() {
        System.out.println("Verifying success message is displayed");
        boolean isSuccessMessageDisplayed = viewSalesListPage.isDeleteSuccessMessageDisplayed();
        System.out.println("Success message displayed: " + isSuccessMessageDisplayed);
        assertThat(isSuccessMessageDisplayed)
            .as("Success message should be displayed after deletion")
            .isTrue();
    }
}
