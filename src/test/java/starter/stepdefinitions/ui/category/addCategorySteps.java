package starter.stepdefinitions.ui.category;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import starter.pages.LoginPage;
import starter.pages.Category.AddCategoryPage;
import net.serenitybdd.annotations.Managed;
import org.openqa.selenium.WebDriver;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class addCategorySteps {

    @Managed
    WebDriver driver;

    LoginPage loginPage = new LoginPage();
    AddCategoryPage addCategoryPage = new AddCategoryPage();

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
        addCategoryPage.openPage();
        assertTrue(addCategoryPage.isOnCategoryListPage(), "Admin is not on category list page");
        System.out.println("Admin is on category list page");
    }

    @When("Admin clicks Add Category button")
    public void adminClicksAddCategoryButton() {
        assertTrue(addCategoryPage.isAddCategoryButtonVisible(), "Add Category button is not visible");
        assertTrue(addCategoryPage.isAddCategoryButtonClickable(), "Add Category button is not clickable");
        addCategoryPage.clickAddCategoryButton();
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

    @Then("Category {string} is saved successfully")
    public void categoryIsSavedSuccessfully(String categoryName) {
        // Wait for the save operation to complete
        addCategoryPage.waitForSaveCompletion();
        System.out.println("Category '" + categoryName + "' save operation completed");
    }

    @And("System displays a success message")
    public void systemDisplaysSuccessMessage() {
        assertTrue(addCategoryPage.isSuccessMessageDisplayed(), "Success message is not displayed");
        System.out.println("System displays a success message");
    }

    @And("System navigates back to the Category List page")
    public void systemNavigatesBackToCategoryListPage() {
        assertTrue(addCategoryPage.isOnCategoryListPage(), "System did not navigate back to Category List page");
        System.out.println("System navigated back to the Category List page");
    }

    @And("Newly added {string} category appears in the category list")
    public void newlyAddedCategoryAppearsInCategoryList(String categoryName) {
        assertTrue(addCategoryPage.isCategoryInList(categoryName),
                "Category '" + categoryName + "' does not appear in the category list");
        System.out.println("Newly added '" + categoryName + "' category appears in the category list");
    }

    @And("Admin leaves Category Name field empty")
    public void adminLeavesCategoryNameFieldEmpty() {
        // Verify that the category name input field is empty
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
        // Verify we are still on the Add Category page (not redirected to category
        // list)
        assertTrue(addCategoryPage.isOnAddCategoryPage(),
                "System navigated away from Add Category page - category may have been created");
        System.out.println("Category is not created - still on Add Category page");
    }
}