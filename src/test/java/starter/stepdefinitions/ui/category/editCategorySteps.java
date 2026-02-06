package starter.stepdefinitions.ui.category;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import net.serenitybdd.annotations.Steps;
import starter.pages.Category.EditCategoryPage;
import starter.pages.Category.CategoryPage;

import static org.assertj.core.api.Assertions.assertThat;

public class editCategorySteps {

    @Steps
    EditCategoryPage editCategoryPage;

    @Steps
    CategoryPage categoryPage;

    // ========== WHEN STEPS ==========

    @When("Admin clicks Edit icon for existing category {string}")
    public void adminClicksEditIconForExistingCategory(String categoryName) {
        try {
            categoryPage.clickEditIconForCategory(categoryName);
            logSuccess("Admin clicked Edit icon for category: " + categoryName);
        } catch (Exception e) {
            logError("Failed to click Edit icon for category '" + categoryName + "': " + e.getMessage());
            throw new AssertionError("Could not click Edit icon for category: " + categoryName, e);
        }
    }

    @When("Admin updates Category Name to {string}")
    public void adminUpdatesCategoryNameTo(String newCategoryName) {
        editCategoryPage.updateCategoryName(newCategoryName);
        logSuccess("Admin updated category name to: " + newCategoryName);
    }

    // ========== AND STEPS - VERIFICATION ==========

    @And("Category exists with name {string}")
    public void categoryExistsWithName(String categoryName) {
        assertThat(categoryPage.isCategoryInList(categoryName))
                .as("Category '" + categoryName + "' should exist in the category list")
                .isTrue();
        logSuccess("Category '" + categoryName + "' exists in the category list");
    }

    @And("Admin is on the Edit Category page")
    public void adminIsOnEditCategoryPage() {
        assertThat(editCategoryPage.isOnEditCategoryPage())
                .as("Admin should be on the Edit Category page")
                .isTrue();
        logSuccess("Admin is on the Edit Category page");
    }

    @And("Updated category {string} appears in the category list")
    public void updatedCategoryAppearsInCategoryList(String updatedCategoryName) {
        categoryPage.pause(500);

        assertThat(categoryPage.isCategoryInList(updatedCategoryName))
                .as("Updated category '" + updatedCategoryName + "' should appear in the category list")
                .isTrue();

        logSuccess("Updated category '" + updatedCategoryName + "' appears in the category list");
    }

    @And("Old category name {string} is no longer visible in the list")
    public void oldCategoryNameIsNoLongerVisibleInList(String oldCategoryName) {
        categoryPage.pause(500);

        assertThat(categoryPage.isCategoryInList(oldCategoryName))
                .as("Old category name '" + oldCategoryName + "' should not be visible in the list")
                .isFalse();

        logSuccess("Old category name '" + oldCategoryName + "' is no longer visible in the list");
    }

    @And("Category details are not updated")
    public void categoryDetailsAreNotUpdated() {
        assertThat(editCategoryPage.isOnEditCategoryPage())
                .as("Should still be on Edit Category page - category should not be updated")
                .isTrue();
        logSuccess("Category details are not updated - still on Edit Category page");
    }

    // ========== THEN STEPS ==========

    @Then("Edit Category page for {string} category is displayed")
    public void editCategoryPageForCategoryIsDisplayed(String categoryName) {
        assertThat(editCategoryPage.isOnEditCategoryPage())
                .as("Edit Category page should be displayed")
                .isTrue();

        String currentCategoryName = editCategoryPage.getCategoryNameValue();
        assertThat(currentCategoryName)
                .as("Edit page should display the correct category")
                .isEqualTo(categoryName);

        logSuccess("Edit Category page for '" + categoryName + "' is displayed");
    }

    @Then("Category details are updated successfully")
    public void categoryDetailsAreUpdatedSuccessfully() {
        editCategoryPage.waitForSaveCompletion();
        categoryPage.isOnCategoryListPage();
        logSuccess("Category details updated successfully");
    }

    @Then("Updated category name {string} is entered successfully")
    public void updatedCategoryNameIsEnteredSuccessfully(String expectedCategoryName) {
        String actualCategoryName = editCategoryPage.getCategoryNameValue();

        assertThat(actualCategoryName)
                .as("Category name should be updated correctly")
                .isEqualTo(expectedCategoryName);

        logSuccess("Updated category name '" + expectedCategoryName + "' is entered successfully");
    }

    // ========== LOGGING UTILITIES ==========

    private void logSuccess(String message) {
        System.out.println(message);
    }

    private void logError(String message) {
        System.err.println(message);
    }
}