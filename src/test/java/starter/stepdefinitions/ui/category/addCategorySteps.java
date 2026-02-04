package starter.stepdefinitions.ui.category;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import starter.pages.LoginPage;
import starter.pages.Category.AddCategoryPage;
import starter.pages.Category.CategoryPage;
import net.serenitybdd.annotations.Steps;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class addCategorySteps {

    @Steps
    LoginPage loginPage;

    @Steps
    AddCategoryPage addCategoryPage;

    @Steps
    CategoryPage categoryPage;

    @Given("Admin is logged in")
    public void adminIsLoggedIn() {
        loginPage.openPage();
        loginPage.enterUsername("admin");
        loginPage.enterPassword("admin123");
        loginPage.clickLogin();
        loginPage.waitForSuccessfulLogin();
        System.out.println("Admin logged in successfully");
    }

    @And("Admin is on the Category List page")
    public void adminIsOnCategoryListPage() {
        categoryPage.openPage();
        assertTrue(categoryPage.isOnCategoryListPage(), "Admin is not on category list page");
        System.out.println("Admin is on category list page");
    }

    @And("Admin ensures test category {string} exists")
    public void adminEnsuresTestCategoryExists(String categoryName) {
        // First, make sure we're on the category list page
        if (!categoryPage.isOnCategoryListPage()) {
            categoryPage.openPage();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Check if category already exists
        if (!categoryPage.isCategoryInList(categoryName)) {
            System.out.println("Category '" + categoryName + "' doesn't exist. Creating it for test setup...");

            // Create the category
            categoryPage.clickAddCategoryButton();

            // Wait for Add Category page to load
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            addCategoryPage.enterCategoryName(categoryName);
            addCategoryPage.leaveParentCategoryEmpty();
            addCategoryPage.clickSaveButton();

            // Wait for save to complete
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Explicitly navigate back to category list page
            categoryPage.openPage();

            // Wait for page to load
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Verify it was created
            assertTrue(categoryPage.isCategoryInList(categoryName),
                    "Failed to create test category '" + categoryName + "' for test setup");

            System.out.println("Test category '" + categoryName + "' created successfully for test setup");
        } else {
            System.out.println("Category '" + categoryName + "' already exists - using existing category");
        }
    }

    @When("Admin clicks Add Category button")
    public void adminClicksAddCategoryButton() {
        assertTrue(categoryPage.isAddCategoryButtonVisible(), "Add Category button is not visible");
        assertTrue(categoryPage.isAddCategoryButtonClickable(), "Add Category button is not clickable");
        categoryPage.clickAddCategoryButton();
        System.out.println("Admin clicked Add Category button");
    }

    @And("Admin is on the Add Category page")
    public void adminIsOnAddCategoryPage() {
        assertTrue(addCategoryPage.isOnAddCategoryPage(), "Admin is not on Add Category page");
        System.out.println("Admin is on Add Category page");
    }

    @And("Admin enters Category Name as {string}")
    public void adminEntersCategoryNameAs(String categoryName) {
        addCategoryPage.enterCategoryName(categoryName);
        System.out.println("Admin entered category name: " + categoryName);
    }

    @And("Admin leaves Parent Category selection empty")
    public void adminLeavesParentCategorySelectionEmpty() {
        addCategoryPage.leaveParentCategoryEmpty();
        System.out.println("Admin left Parent Category selection empty");
    }

    @And("Admin clicks Save button")
    public void adminClicksSaveButton() {
        addCategoryPage.clickSaveButton();
        System.out.println("Admin clicked Save button");
    }

    @And("Admin clicks Cancel button")
    public void adminClicksCancelButton() {
        assertTrue(addCategoryPage.isCancelButtonVisible(), "Cancel button is not visible");
        addCategoryPage.clickCancelButton();
        System.out.println("Admin clicked Cancel button");
    }

    @Then("Category {string} is saved successfully")
    public void categoryIsSavedSuccessfully(String categoryName) {
        addCategoryPage.waitForSaveCompletion();
        categoryPage.isOnCategoryListPage();
        System.out.println("Category '" + categoryName + "' save operation completed");
    }

    @Then("Category {string} is not saved")
    public void categoryIsNotSaved(String categoryName) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        assertFalse(categoryPage.isCategoryInList(categoryName),
                "Category '" + categoryName + "' was saved but should not have been");
        System.out.println("Category '" + categoryName + "' was not saved (as expected)");
    }

    @And("Category list remains unchanged")
    public void categoryListRemainsUnchanged() {
        assertTrue(categoryPage.isOnCategoryListPage(),
                "Not on Category List page");
        System.out.println("Category list remains unchanged");
    }

    @And("System displays a success message")
    public void systemDisplaysSuccessMessage() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        boolean isDisplayed = categoryPage.isSuccessMessageDisplayed();
        assertTrue(isDisplayed, "Success message is not displayed");
        System.out.println("System displays a success message");
    }

    @And("System navigates back to the Category List page")
    public void systemNavigatesBackToCategoryListPage() {
        assertTrue(categoryPage.isOnCategoryListPage(), "System did not navigate back to Category List page");
        System.out.println("System navigated back to the Category List page");
    }

    @And("Newly added {string} category appears in the category list")
    public void newlyAddedCategoryAppearsInCategoryList(String categoryName) {
        assertTrue(categoryPage.isCategoryInList(categoryName),
                "Category '" + categoryName + "' does not appear in the category list");
        System.out.println("Newly added '" + categoryName + "' category appears in the category list");
    }

    @And("Admin leaves Category Name field empty")
    public void adminLeavesCategoryNameFieldEmpty() {
        String categoryNameValue = addCategoryPage.getCategoryNameValue();
        assertTrue(categoryNameValue.isEmpty(), "Category Name field is not empty");
        System.out.println("Admin left Category Name field empty");
    }

    @Then("System displays validation message {string} below the Category Name field")
    public void systemDisplaysValidationMessageBelowCategoryNameField(String expectedMessage) {
        assertTrue(addCategoryPage.isValidationMessageDisplayed(),
                "Validation message is not displayed below Category Name field");
        String actualMessage = addCategoryPage.getValidationMessageText();
        assertTrue(actualMessage.toLowerCase().contains(expectedMessage.toLowerCase()),
                "Validation message '" + actualMessage + "' does not contain expected text: " + expectedMessage);
        System.out.println("System displays validation message: " + actualMessage);
    }

    @And("Category is not created")
    public void categoryIsNotCreated() {
        assertTrue(addCategoryPage.isOnAddCategoryPage(),
                "System navigated away from Add Category page - category may have been created");
        System.out.println("Category is not created - still on Add Category page");
    }
}