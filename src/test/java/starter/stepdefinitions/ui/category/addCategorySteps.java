package starter.stepdefinitions.ui.category;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import net.serenitybdd.annotations.Steps;
import starter.pages.LoginPage;
import starter.pages.Category.AddCategoryPage;
import starter.pages.Category.CategoryPage;

import static org.assertj.core.api.Assertions.assertThat;

public class addCategorySteps {

    @Steps
    LoginPage loginPage;

    @Steps
    AddCategoryPage addCategoryPage;

    @Steps
    CategoryPage categoryPage;

    // ========== GIVEN STEPS ==========

    @Given("Admin is logged in")
    public void adminIsLoggedIn() {
        loginPage.openPage();
        loginPage.enterUsername("admin");
        loginPage.enterPassword("admin123");
        loginPage.clickLogin();
        loginPage.waitForSuccessfulLogin();
        logSuccess("Admin logged in successfully");
    }

    // ========== WHEN STEPS ==========

    @When("Admin clicks Add Category button")
    public void adminClicksAddCategoryButton() {
        assertThat(categoryPage.isAddCategoryButtonVisible())
                .as("Add Category button should be visible")
                .isTrue();

        assertThat(categoryPage.isAddCategoryButtonClickable())
                .as("Add Category button should be clickable")
                .isTrue();

        categoryPage.clickAddCategoryButton();
        logSuccess("Admin clicked Add Category button");
    }

    // ========== AND STEPS - PAGE NAVIGATION ==========

    @And("Admin is on the Category List page")
    public void adminIsOnCategoryListPage() {
        categoryPage.openPage();
        assertThat(categoryPage.isOnCategoryListPage())
                .as("Admin should be on category list page")
                .isTrue();
        logSuccess("Admin is on category list page");
    }

    @And("Admin is on the Add Category page")
    public void adminIsOnAddCategoryPage() {
        assertThat(addCategoryPage.isOnAddCategoryPage())
                .as("Admin should be on Add Category page")
                .isTrue();
        logSuccess("Admin is on Add Category page");
    }

    // ========== AND STEPS - DATA SETUP ==========

    @And("Admin ensures test category {string} exists")
    public void adminEnsuresTestCategoryExists(String categoryName) {
        ensureOnCategoryListPage();

        if (!categoryPage.isCategoryInList(categoryName)) {
            logInfo("Category '" + categoryName + "' doesn't exist. Creating it for test setup...");
            createTestCategory(categoryName);
            verifyTestCategoryCreated(categoryName);
        } else {
            logInfo("Category '" + categoryName + "' already exists - using existing category");
        }
    }

    // ========== AND STEPS - USER ACTIONS ==========

    @And("Admin enters Category Name as {string}")
    public void adminEntersCategoryNameAs(String categoryName) {
        addCategoryPage.enterCategoryName(categoryName);
        logSuccess("Admin entered category name: " + categoryName);
    }

    @And("Admin leaves Parent Category selection empty")
    public void adminLeavesParentCategorySelectionEmpty() {
        addCategoryPage.leaveParentCategoryEmpty();
        logSuccess("Admin left Parent Category selection empty");
    }

    @And("Admin leaves Category Name field empty")
    public void adminLeavesCategoryNameFieldEmpty() {
        String categoryNameValue = addCategoryPage.getCategoryNameValue();
        assertThat(categoryNameValue)
                .as("Category Name field should be empty")
                .isEmpty();
        logSuccess("Admin left Category Name field empty");
    }

    @And("Admin clicks Save button")
    public void adminClicksSaveButton() {
        addCategoryPage.clickSaveButton();
        logSuccess("Admin clicked Save button");
    }

    @And("Admin clicks Cancel button")
    public void adminClicksCancelButton() {
        assertThat(addCategoryPage.isCancelButtonVisible())
                .as("Cancel button should be visible")
                .isTrue();

        addCategoryPage.clickCancelButton();
        logSuccess("Admin clicked Cancel button");
    }

    // ========== THEN STEPS - VERIFICATION ==========

    @Then("Category {string} is saved successfully")
    public void categoryIsSavedSuccessfully(String categoryName) {
        addCategoryPage.waitForSaveCompletion();
        categoryPage.isOnCategoryListPage();
        logSuccess("Category '" + categoryName + "' save operation completed");
    }

    @Then("Category {string} is not saved")
    public void categoryIsNotSaved(String categoryName) {
        categoryPage.pause(500);

        assertThat(categoryPage.isCategoryInList(categoryName))
                .as("Category '" + categoryName + "' should not be saved")
                .isFalse();

        logSuccess("Category '" + categoryName + "' was not saved (as expected)");
    }

    @Then("System displays validation message {string} below the Category Name field")
    public void systemDisplaysValidationMessageBelowCategoryNameField(String expectedMessage) {
        assertThat(addCategoryPage.isValidationMessageDisplayed())
                .as("Validation message should be displayed below Category Name field")
                .isTrue();

        String actualMessage = addCategoryPage.getValidationMessageText();
        assertThat(actualMessage.toLowerCase())
                .as("Validation message should contain expected text")
                .contains(expectedMessage.toLowerCase());

        logSuccess("System displays validation message: " + actualMessage);
    }

    // ========== AND STEPS - POST-ACTION VERIFICATION ==========

    @And("Category list remains unchanged")
    public void categoryListRemainsUnchanged() {
        assertThat(categoryPage.isOnCategoryListPage())
                .as("Should be on Category List page")
                .isTrue();
        logSuccess("Category list remains unchanged");
    }

    @And("System displays a success message")
    public void systemDisplaysSuccessMessage() {
        categoryPage.pause(1000);

        assertThat(categoryPage.isSuccessMessageDisplayed())
                .as("Success message should be displayed")
                .isTrue();

        logSuccess("System displays a success message");
    }

    @And("System navigates back to the Category List page")
    public void systemNavigatesBackToCategoryListPage() {
        assertThat(categoryPage.isOnCategoryListPage())
                .as("System should navigate back to Category List page")
                .isTrue();
        logSuccess("System navigated back to the Category List page");
    }

    @And("Newly added {string} category appears in the category list")
    public void newlyAddedCategoryAppearsInCategoryList(String categoryName) {
        assertThat(categoryPage.isCategoryInList(categoryName))
                .as("Category '" + categoryName + "' should appear in the category list")
                .isTrue();
        logSuccess("Newly added '" + categoryName + "' category appears in the category list");
    }

    @And("Category is not created")
    public void categoryIsNotCreated() {
        assertThat(addCategoryPage.isOnAddCategoryPage())
                .as("Should still be on Add Category page - category should not be created")
                .isTrue();
        logSuccess("Category is not created - still on Add Category page");
    }

    // ========== HELPER METHODS ==========

    private void ensureOnCategoryListPage() {
        if (!categoryPage.isOnCategoryListPage()) {
            categoryPage.openPage();
            categoryPage.pause(1000);
        }
    }

    private void createTestCategory(String categoryName) {
        categoryPage.clickAddCategoryButton();
        categoryPage.pause(1000);

        addCategoryPage.enterCategoryName(categoryName);
        addCategoryPage.leaveParentCategoryEmpty();
        addCategoryPage.clickSaveButton();

        categoryPage.pause(2000);
        categoryPage.openPage();
        categoryPage.pause(1000);
    }

    private void verifyTestCategoryCreated(String categoryName) {
        assertThat(categoryPage.isCategoryInList(categoryName))
                .as("Test category '" + categoryName + "' should be created")
                .isTrue();
        logSuccess("Test category '" + categoryName + "' created successfully for test setup");
    }

    // ========== LOGGING UTILITIES ==========

    private void logSuccess(String message) {
        System.out.println(message);
    }

    private void logInfo(String message) {
        System.out.println(message);
    }
}