package starter.stepdefinitions.ui.category;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import starter.pages.Category.EditCategoryPage;
import starter.pages.Category.CategoryPage;
import net.serenitybdd.annotations.Steps;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class editCategorySteps {

    @Steps
    EditCategoryPage editCategoryPage;

    @Steps
    CategoryPage categoryPage;

    @And("Category exists with name {string}")
    public void categoryExistsWithName(String categoryName) {
        boolean categoryExists = categoryPage.isCategoryInList(categoryName);
        assertTrue(categoryExists, "Category '" + categoryName + "' does not exist in the category list");
        System.out.println("Category '" + categoryName + "' exists in the category list");
    }

    @When("Admin clicks Edit icon for existing category {string}")
    public void adminClicksEditIconForExistingCategory(String categoryName) {
        try {
            categoryPage.clickEditIconForCategory(categoryName);
            System.out.println("Admin clicked Edit icon for category: " + categoryName);
        } catch (Exception e) {
            System.out.println("Failed to click Edit icon for category '" + categoryName + "': " + e.getMessage());
            throw e;
        }
    }

    @Then("Edit Category page for {string} category is displayed")
    public void editCategoryPageForCategoryIsDisplayed(String categoryName) {
        assertTrue(editCategoryPage.isOnEditCategoryPage(),
                "Edit Category page is not displayed");

        String currentCategoryName = editCategoryPage.getCategoryNameValue();
        assertEquals(categoryName, currentCategoryName,
                "Edit page is not displaying the correct category. Expected: '" + categoryName +
                        "', but found: '" + currentCategoryName + "'");

        System.out.println("Edit Category page for '" + categoryName + "' is displayed");
    }

    @When("Admin updates Category Name to {string}")
    public void adminUpdatesCategoryNameTo(String newCategoryName) {
        editCategoryPage.updateCategoryName(newCategoryName);
        System.out.println("Admin updated category name to: " + newCategoryName);
    }

    @Then("Category details are updated successfully")
    public void categoryDetailsAreUpdatedSuccessfully() {
        editCategoryPage.waitForSaveCompletion();
        categoryPage.isOnCategoryListPage();
        System.out.println("Category details updated successfully");
    }

    @And("Updated category {string} appears in the category list")
    public void updatedCategoryAppearsInCategoryList(String updatedCategoryName) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        boolean categoryExists = categoryPage.isCategoryInList(updatedCategoryName);
        assertTrue(categoryExists,
                "Updated category '" + updatedCategoryName + "' does not appear in the category list");
        System.out.println("Updated category '" + updatedCategoryName + "' appears in the category list");
    }

    @And("Old category name {string} is no longer visible in the list")
    public void oldCategoryNameIsNoLongerVisibleInList(String oldCategoryName) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        boolean categoryExists = categoryPage.isCategoryInList(oldCategoryName);
        assertTrue(!categoryExists,
                "Old category name '" + oldCategoryName + "' is still visible in the list");
        System.out.println("Old category name '" + oldCategoryName + "' is no longer visible in the list");
    }

    @Then("Updated category name {string} is entered successfully")
    public void updatedCategoryNameIsEnteredSuccessfully(String expectedCategoryName) {
        String actualCategoryName = editCategoryPage.getCategoryNameValue();
        assertEquals(expectedCategoryName, actualCategoryName,
                "Category name was not updated correctly. Expected: '" + expectedCategoryName +
                        "', but found: '" + actualCategoryName + "'");
        System.out.println("Updated category name '" + expectedCategoryName + "' is entered successfully");
    }

    @And("Admin is on the Edit Category page")
    public void adminIsOnEditCategoryPage() {
        assertTrue(editCategoryPage.isOnEditCategoryPage(),
                "Admin is not on the Edit Category page");
        System.out.println("Admin is on the Edit Category page");
    }

    @And("Category details are not updated")
    public void categoryDetailsAreNotUpdated() {
        assertTrue(editCategoryPage.isOnEditCategoryPage(),
                "System navigated away from Edit Category page - category may have been updated");
        System.out.println("Category details are not updated - still on Edit Category page");
    }
}